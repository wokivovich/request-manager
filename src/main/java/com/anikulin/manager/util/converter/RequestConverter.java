package com.anikulin.manager.util.converter;

import com.anikulin.manager.entity.RequestStatus;
import com.anikulin.manager.dto.RequestDto;
import com.anikulin.manager.entity.Request;
import com.anikulin.manager.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestConverter {

    public static RequestDto convertRequestToDto(Request request) {

        return RequestDto.builder()
                .id(request.getId())
                .text(request.getText())
                .creationDate(request.getCreationDate())
                .status(request.getStatus())
                .build();
    }

    public static Request convertDtoToRequest(RequestDto requestDto) {

        return Request.builder()
                .text(requestDto.getText())
                .creationDate(LocalDateTime.now())
                .status(RequestStatus.DRAFT)
                .author((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build();
    }
}
