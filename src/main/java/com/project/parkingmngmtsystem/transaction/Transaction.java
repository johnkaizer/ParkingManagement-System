package com.project.parkingmngmtsystem.transaction;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import com.project.parkingmngmtsystem.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Integer spaceId;
    private String carNumber;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;

}
