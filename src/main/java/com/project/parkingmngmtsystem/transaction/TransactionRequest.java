package com.project.parkingmngmtsystem.transaction;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private Long userId;
    private Integer spaceId;
    private String carNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
}
