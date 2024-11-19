package com.project.parkingmngmtsystem.booking;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBookingService {
    private final CarBookingRepository carBookingRepository;

    public CarBookingService(CarBookingRepository carBookingRepository) {
        this.carBookingRepository = carBookingRepository;
    }

    public CarBooking createBooking(CarBooking carBooking) {
        return carBookingRepository.save(carBooking);
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
        existingBooking.setName(updatedBooking.getName());
        existingBooking.setCarNumber(updatedBooking.getCarNumber());
        existingBooking.setAmount(updatedBooking.getAmount());
        existingBooking.setBookingDateTime(updatedBooking.getBookingDateTime());
        existingBooking.setUseDateTime(updatedBooking.getUseDateTime());
        return carBookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        carBookingRepository.deleteById(id);
    }
}

