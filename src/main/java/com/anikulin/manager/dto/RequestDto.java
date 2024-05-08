package com.anikulin.manager.dto;

import com.anikulin.manager.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;
    private String text;
    private RequestStatus status;
    private LocalDateTime creationDate;
}
