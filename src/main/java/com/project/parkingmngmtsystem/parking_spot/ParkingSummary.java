package com.project.parkingmngmtsystem.parking_spot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSummary {
    private String location;
    private long totalSpots;
    private long unoccupiedSpots;
}

