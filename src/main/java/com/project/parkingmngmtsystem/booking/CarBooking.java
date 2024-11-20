package com.project.parkingmngmtsystem.booking;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class CarBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String customerName;

    private String carNumber;

    private Double amount;

    private Integer spotNumber;

    private String location;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookingDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime useDateTime;

    @PrePersist
    @PreUpdate
    public void validateUseDateTime() {
        if (useDateTime.isAfter(bookingDateTime.plusDays(1))) {
            throw new IllegalArgumentException("Usage time cannot be more than 24 hours from the booking time");
        }
    }
}
