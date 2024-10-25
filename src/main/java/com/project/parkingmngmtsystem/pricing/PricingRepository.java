package com.project.parkingmngmtsystem.pricing;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {
    Optional<Pricing> findByParkingSpaceAndTimeRange(ParkingSpace parkingSpace, String timeRange);
}

