package com.project.parkingmngmtsystem.parking_spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

