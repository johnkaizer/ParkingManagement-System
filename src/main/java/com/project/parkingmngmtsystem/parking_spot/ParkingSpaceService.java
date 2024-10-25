package com.project.parkingmngmtsystem.parking_spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    public List<ParkingSpace> findAll() {
        return parkingSpaceRepository.findAll();
    }

    public Optional<ParkingSpace> findById(Long id) {
        return parkingSpaceRepository.findById(id);
    }

    public ParkingSpace save(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    public void deleteById(Long id) {
        parkingSpaceRepository.deleteById(id);
    }

    public ParkingSpace updateStatus(Long id, String occupancyStatus) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Space not found"));
        parkingSpace.setOccupancyStatus(occupancyStatus);
        return parkingSpaceRepository.save(parkingSpace);
    }
}

