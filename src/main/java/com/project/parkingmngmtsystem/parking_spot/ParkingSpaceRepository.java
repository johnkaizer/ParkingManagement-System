package com.project.parkingmngmtsystem.parking_spot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    // Get the total number of parking spaces
    long count();

    // Get the number of unoccupied parking spaces
    long countByIsOccupiedFalse();

    // Find parking spaces by lot location
    List<ParkingSpace> findByLotLocationContainingIgnoreCase(String lotLocation);

    // Find all unoccupied parking spaces
    List<ParkingSpace> findByIsOccupiedFalse();

    List<ParkingSpace> findByIsOccupied(boolean b);
}

