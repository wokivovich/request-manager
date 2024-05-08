package com.anikulin.manager.controller;

import com.anikulin.manager.dto.RequestDto;
import com.anikulin.manager.service.RequestService;
import com.anikulin.manager.service.UserService;
import com.anikulin.manager.util.converter.RequestConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final RequestService requestService;
    private final UserService userService;

    public OperatorController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/all/{page}/{order}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<List<RequestDto>> getShippedRequests(@PathVariable int page, @PathVariable String order) {
        List<RequestDto> requestsDto = requestService.findAllShippedRequests(page, order)
                .stream()
                .map(RequestConverter::convertRequestToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(requestsDto, HttpStatus.OK);
    }

    @GetMapping("shipped/{username}/all/{page}/{order}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<List<RequestDto>> getUserShippedRequests(
            @PathVariable String username,
            @PathVariable int page,
            @PathVariable String order
    ) {
        List<RequestDto> requestsDto = requestService.findAllUserShippedRequests(
                    userService.findUserByUsernameSnippet(username), page, order
                ).stream()
                .map(RequestConverter::convertRequestToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(requestsDto, HttpStatus.OK);
    }

    @GetMapping("/apply/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<String> applyRequestById(@PathVariable Long id) {
        requestService.applyRequest(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/deny/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<String> denyRequestById(@PathVariable Long id) {
        requestService.denyRequest(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/request/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id) {

        return new ResponseEntity<>(
                RequestConverter.convertRequestToDto(requestService.findRequestById(id)),
                HttpStatus.OK
        );
    }

}
