package com.project.parkingmngmtsystem.booking;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import com.project.parkingmngmtsystem.parking_spot.ParkingSpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class CarBookingController {
    private final CarBookingService carBookingService;
    private final ParkingSpaceService parkingSpaceService;

    public CarBookingController(CarBookingService carBookingService, ParkingSpaceService parkingSpaceService) {
        this.carBookingService = carBookingService;
        this.parkingSpaceService = parkingSpaceService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody CarBooking booking) {
        try {
            carBookingService.createBooking(booking);

            ParkingSpace parkingSpace = parkingSpaceService.findBySpotNumber(booking.getSpotNumber());
            if (parkingSpace != null) {
                parkingSpace.setOccupied(true);
                parkingSpaceService.save(parkingSpace);
            } else {
                throw new RuntimeException("Parking spot not found");
            }

            return ResponseEntity.ok("Booking successful!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
    @GetMapping("/daily-trends")
    public ResponseEntity<Map<String, Long>> getDailyBookingTrends() {
        Map<String, Long> dailyTrends = carBookingService.getDailyBookingTrends();
        return ResponseEntity.ok(dailyTrends);
    }
    @GetMapping("/search")
    public ResponseEntity<List<CarBooking>> searchBookingsByName(@RequestParam String name) {
        List<CarBooking> bookings = carBookingService.searchBookingsByName(name);
        return ResponseEntity.ok(bookings);
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        carBookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

}
