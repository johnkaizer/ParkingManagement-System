package com.project.parkingmngmtsystem.booking;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import com.project.parkingmngmtsystem.parking_spot.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarBookingService {
    private final CarBookingRepository carBookingRepository;

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    public CarBookingService(CarBookingRepository carBookingRepository) {
        this.carBookingRepository = carBookingRepository;
    }

    public CarBooking createBooking(CarBooking booking) {
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setExpiryDateTime(booking.getUseDateTime().plusHours(4));
        return carBookingRepository.save(booking);
    }

    public List<CarBooking> getAllBookings() {
        return carBookingRepository.findAll();
    }

    public CarBooking getBookingById(Long id) {
        return carBookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));
    }

    public CarBooking updateBooking(Long id, CarBooking updatedBooking) {
        CarBooking existingBooking = getBookingById(id);
        existingBooking.setCustomerName(updatedBooking.getCustomerName());
        existingBooking.setCarNumber(updatedBooking.getCarNumber());
        existingBooking.setAmount(updatedBooking.getAmount());
        existingBooking.setBookingDateTime(updatedBooking.getBookingDateTime());
        existingBooking.setUseDateTime(updatedBooking.getUseDateTime());
        return carBookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        CarBooking booking = carBookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // Update parking space status
        ParkingSpace space = parkingSpaceService.findBySpotNumber(booking.getSpotNumber());
        if (space != null) {
            space.setOccupied(false);
            parkingSpaceService.save(space);
        }

        // Delete the booking
        carBookingRepository.deleteById(id);
    }


    public Map<String, Long> getDailyBookingTrends() {
        List<Object[]> trends = carBookingRepository.countDailyBookings();
        Map<String, Long> dailyTrends = new HashMap<>();

        for (Object[] trend : trends) {
            String date = (String) trend[0];
            Long count = (Long) trend[1];
            dailyTrends.put(date, count);
        }

        return dailyTrends;
    }

    public List<CarBooking> searchBookingsByName(String name) {
        return carBookingRepository.findByCustomerNameContainingIgnoreCase(name);
    }

    public void cancelBooking(Long id) {
        CarBooking booking = getBookingById(id);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime useDateTime = booking.getUseDateTime();

        if (useDateTime.isBefore(now)) {
            throw new IllegalStateException("Booking has already started or expired and cannot be canceled.");
        }

        if (useDateTime.minusHours(12).isBefore(now)) {
            throw new IllegalStateException("Bookings within 12 hours of use cannot be canceled.");
        }

        // Logic to handle cancellation
        ParkingSpace parkingSpace = parkingSpaceService.findBySpotNumber(booking.getSpotNumber());
        if (parkingSpace != null) {
            parkingSpace.setOccupied(false);
            parkingSpaceService.save(parkingSpace);
        }

        carBookingRepository.deleteById(id);
    }


}

