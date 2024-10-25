package com.project.parkingmngmtsystem.parking_spot;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "spots")
@Data
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingSpaceId;

    private String lotLocation;
    private Integer spotNumber;
    private String pricingRules;
    private String occupancyStatus;

}
