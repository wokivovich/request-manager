package com.anikulin.manager.repository;

import com.anikulin.manager.entity.RequestStatus;
import com.anikulin.manager.entity.Request;
import com.anikulin.manager.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    List<Request> findAllByAuthor(User user, Pageable pageable);

    List<Request> findAllByStatus(RequestStatus shipped, Pageable pageRequest);

    List<Request> findAllByAuthorAndStatus(User user, RequestStatus status, Pageable pageRequest);

    Optional<Request> findByIdAndAuthor(Long requestId, User user);

    Optional<Request> findByIdAndStatus(Long requestId, RequestStatus status);
}
