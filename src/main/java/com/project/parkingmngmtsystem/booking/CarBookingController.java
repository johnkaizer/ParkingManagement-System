package com.project.parkingmngmtsystem.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class CarBookingController {
    private final CarBookingService carBookingService;

    public CarBookingController(CarBookingService carBookingService) {
        this.carBookingService = carBookingService;
    }

    @PostMapping
    public ResponseEntity<CarBooking> createBooking( @RequestBody CarBooking carBooking) {
        CarBooking createdBooking = carBookingService.createBooking(carBooking);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping
    public ResponseEntity<List<CarBooking>> getAllBookings() {
        return ResponseEntity.ok(carBookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarBooking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(carBookingService.getBookingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarBooking> updateBooking(@PathVariable Long id,  @RequestBody CarBooking carBooking) {
        CarBooking updatedBooking = carBookingService.updateBooking(id, carBooking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        carBookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
