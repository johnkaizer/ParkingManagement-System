package com.project.parkingmngmtsystem.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {

    @Query("SELECT CAST(cb.bookingDateTime AS date) AS bookingDate, COUNT(cb.id) AS totalBookings " +
            "FROM CarBooking cb " +
            "GROUP BY CAST(cb.bookingDateTime AS date) " +
            "ORDER BY totalBookings")
    List<Object[]> countDailyBookings();

    List<CarBooking> findByCustomerNameContainingIgnoreCase(String name);
}
