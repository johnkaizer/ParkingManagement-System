package com.project.parkingmngmtsystem.booking;

import jakarta.persistence.*;
import lombok.Data;
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
    private LocalDateTime bookingDateTime;
    private LocalDateTime useDateTime;
    private LocalDateTime expiryDateTime;

    @PrePersist
    @PreUpdate
    public void setDefaultsAndValidate() {
        if (bookingDateTime == null) {
            bookingDateTime = LocalDateTime.now();
        }
        if (expiryDateTime == null && useDateTime != null) {
            expiryDateTime = useDateTime.plusHours(1);
        }
    }
}
