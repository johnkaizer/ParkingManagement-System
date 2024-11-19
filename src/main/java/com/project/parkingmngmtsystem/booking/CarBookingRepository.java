package com.project.parkingmngmtsystem.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {
}
