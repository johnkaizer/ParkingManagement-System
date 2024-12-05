package com.project.parkingmngmtsystem.parking_spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-spaces")
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @GetMapping
    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getParkingSpaceById(@PathVariable Long id) {
        return parkingSpaceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ParkingSpace createParkingSpace(@RequestBody ParkingSpace parkingSpace) {
        return parkingSpaceService.save(parkingSpace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> updateOccupancyStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ParkingSpace updatedParkingSpace = parkingSpaceService.updateStatus(id, status);
            return ResponseEntity.ok(updatedParkingSpace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        parkingSpaceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // Endpoint to get the total number of parking spaces
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalParkingSpaces() {
        long totalSpaces = parkingSpaceService.getTotalParkingSpaces();
        return ResponseEntity.ok(totalSpaces);
    }

    // Endpoint to get the number of Available parking spaces
    @GetMapping("/unoccupied")
    public ResponseEntity<Long> getUnoccupiedParkingSpacesCount() {
        long unoccupiedSpaces = parkingSpaceService.getUnoccupiedParkingSpacesCount();
        return ResponseEntity.ok(unoccupiedSpaces);
    }

    // Endpoint to search parking spaces by lot location
    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpace>> searchByLotLocation(@RequestParam String lotLocation) {
        List<ParkingSpace> spaces = parkingSpaceService.searchByLotLocation(lotLocation);
        return ResponseEntity.ok(spaces);
    }

    // Endpoint to get all unoccupied parking spaces
    @GetMapping("/unoccupied/with-pricing")
    public ResponseEntity<List<Map<String, Object>>> getUnoccupiedSpacesWithPricing() {
        List<Map<String, Object>> spacesWithPricing = parkingSpaceService.getUnoccupiedSpacesWithPricing();
        return ResponseEntity.ok(spacesWithPricing);
    }
    @GetMapping("/summary")
    public List<ParkingSummary> getParkingSummary() {
        return parkingSpaceService.getParkingSummary();
    }

}
