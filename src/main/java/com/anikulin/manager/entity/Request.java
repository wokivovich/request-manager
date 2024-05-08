package com.anikulin.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private LocalDateTime creationDate;

    @Enumerated
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
