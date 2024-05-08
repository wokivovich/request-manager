package com.anikulin.manager.controller;

import com.anikulin.manager.dto.RequestDto;
import com.anikulin.manager.entity.User;
import com.anikulin.manager.service.RequestService;
import com.anikulin.manager.util.converter.RequestConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.anikulin.manager.util.converter.RequestConverter.convertDtoToRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RequestService requestService;

    public UserController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createRequest(@RequestBody RequestDto requestDto) {

        requestService.saveRequest(convertDtoToRequest(requestDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/{page}/{order}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RequestDto>> getUserRequests(@PathVariable int page, @PathVariable String order) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        List<RequestDto> requestsDto = requestService.findAllUserRequests(user, page, order)
                .stream()
                .map(RequestConverter::convertRequestToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(requestsDto, HttpStatus.OK);
    }

    @PatchMapping("/ship/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sendRequestToOperator(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        requestService.sendRequestToOperator(user, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> editRequest(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        requestService.editRequest(user, id, requestDto.getText());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
