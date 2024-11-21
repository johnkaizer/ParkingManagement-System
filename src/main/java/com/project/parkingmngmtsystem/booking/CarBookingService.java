package com.project.parkingmngmtsystem.booking;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarBookingService {
    private final CarBookingRepository carBookingRepository;

    public CarBookingService(CarBookingRepository carBookingRepository) {
        this.carBookingRepository = carBookingRepository;
    }

    public CarBooking createBooking(CarBooking booking) {
        booking.setBookingDateTime(LocalDateTime.now());
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
        carBookingRepository.deleteById(id);
    }

    public Map<String, Long> getDailyBookingTrends() {
        List<Object[]> trends = carBookingRepository.countDailyBookings();
        Map<String, Long> dailyTrends = new HashMap<>();

        for (Object[] trend : trends) {
            String date = (String) trend[0]; // Assuming the first column is the date as a String
            Long count = (Long) trend[1];   // Assuming the second column is the booking count
            dailyTrends.put(date, count);
        }

        return dailyTrends;
    }
}

