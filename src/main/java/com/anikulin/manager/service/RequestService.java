package com.anikulin.manager.service;

import com.anikulin.manager.entity.RequestStatus;
import com.anikulin.manager.entity.Request;
import com.anikulin.manager.entity.User;
import com.anikulin.manager.repository.RequestRepository;
import com.anikulin.manager.util.exception.RequestNotFoundException;
import com.anikulin.manager.util.exception.RequestStatusException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private static final int PAGE_SIZE = 5;
    private static final String REQUEST_NOT_FOUND_MESSAGE = "Request not found";

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void saveRequest(Request request) {

        requestRepository.save(request);
    }

    public List<Request> findAllUserRequests(User user, int page, String order) {

        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE,
                order.equals("ascending") ?
                        Sort.by("creationDate").ascending() :
                        Sort.by("creationDate").descending());

        return requestRepository.findAllByAuthor(user, pageRequest);
    }

    @Transactional
    public void sendRequestToOperator(User user, Long requestId) {
        Request request = requestRepository.findByIdAndAuthor(requestId, user).orElseThrow(() ->
                new RequestNotFoundException(REQUEST_NOT_FOUND_MESSAGE));
        request.setStatus(RequestStatus.SHIPPED);

        requestRepository.save(request);
    }

    public void editRequest(User user, Long requestId, String text) throws RequestStatusException {
        Request request = requestRepository.findByIdAndAuthor(requestId, user).orElseThrow(() ->
                new RequestNotFoundException(REQUEST_NOT_FOUND_MESSAGE));
        if (request.getStatus().equals(RequestStatus.DRAFT)) {
            request.setText(text);

            requestRepository.save(request);
        } else {
            throw new RequestStatusException("You can edit only 'draft' status requests");
        }
    }

    public List<Request> findAllShippedRequests(int page, String order) {
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE,
                order.equals("ascending") ?
                        Sort.by("creationDate").ascending() :
                        Sort.by("creationDate").descending());

        return requestRepository.findAllByStatus(RequestStatus.SHIPPED, pageRequest).stream()
                .peek(request -> request.setText(formatMessageForOperator(request.getText())))
                .collect(Collectors.toList());
    }

    public List<Request> findAllUserShippedRequests(User user, int page, String order) {

        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE,
                order.equals("ascending") ?
                        Sort.by("creationDate").ascending() :
                        Sort.by("creationDate").descending());

        return requestRepository.findAllByAuthorAndStatus(user, RequestStatus.SHIPPED, pageRequest).stream()
                .peek(request -> request.setText(formatMessageForOperator(request.getText())))
                .collect(Collectors.toList());
    }

    private String formatMessageForOperator(String text) {

        return text.replaceAll("", "-");
    }

    @Transactional
    public void applyRequest(Long requestId) {
        changeRequestStatus(requestId, RequestStatus.APPLIED);
    }

    @Transactional
    public void denyRequest(Long requestId) {
        changeRequestStatus(requestId, RequestStatus.DENIED);
    }

    private void changeRequestStatus(Long requestId, RequestStatus status) {
        Request request = requestRepository.findByIdAndStatus(requestId, RequestStatus.SHIPPED).orElseThrow(() ->
                new RequestNotFoundException(REQUEST_NOT_FOUND_MESSAGE));
        request.setStatus(status);

        requestRepository.save(request);
    }

    public Request findRequestById(Long requestId) {
        Request request = requestRepository.findByIdAndStatus(requestId, RequestStatus.SHIPPED).orElseThrow(() ->
                new RequestNotFoundException(REQUEST_NOT_FOUND_MESSAGE));
        if (request.getStatus().equals(RequestStatus.SHIPPED)) {

            return request;
        } else {

            throw new RequestStatusException(REQUEST_NOT_FOUND_MESSAGE);
        }
    }
}
