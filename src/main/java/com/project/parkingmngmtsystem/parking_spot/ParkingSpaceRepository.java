package com.project.parkingmngmtsystem.parking_spot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    Optional<Object> findBySpotNumber(Integer spotNumber);

    @Query("SELECT new com.project.parkingmngmtsystem.parking_spot.ParkingSummary(p.lotLocation, COUNT(p), " +
            "SUM(CASE WHEN p.isOccupied = false THEN 1 ELSE 0 END)) " +
            "FROM ParkingSpace p GROUP BY p.lotLocation")
    List<ParkingSummary> getParkingSummary();
}

