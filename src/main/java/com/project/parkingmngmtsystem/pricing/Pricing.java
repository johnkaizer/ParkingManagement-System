package com.project.parkingmngmtsystem.pricing;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pricing")
@Data
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeRange;
    private Double baseRate;
    private Double demandFactor;
    private String dayType; // "weekday", "weekend", "holiday"
    private Double seasonalAdjustment;
    @ManyToOne
    private ParkingSpace parkingSpace;
}
