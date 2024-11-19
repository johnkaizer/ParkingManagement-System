package com.project.parkingmngmtsystem.parking_spot;

import com.project.parkingmngmtsystem.pricing.Pricing;
import com.project.parkingmngmtsystem.pricing.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private PricingRepository pricingRepository;

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

    public ParkingSpace updateStatus(Long id, String isOccupied) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Space not found"));
        parkingSpace.setOccupied(Boolean.parseBoolean(isOccupied));
        return parkingSpaceRepository.save(parkingSpace);
    }
    // Get total parking spaces
    public long getTotalParkingSpaces() {
        return parkingSpaceRepository.count();
    }

    // Get unoccupied parking spaces
    public long getUnoccupiedParkingSpacesCount() {
        return parkingSpaceRepository.countByIsOccupiedFalse();
    }

    // Search parking spaces by lot location
    public List<ParkingSpace> searchByLotLocation(String lotLocation) {
        return parkingSpaceRepository.findByLotLocationContainingIgnoreCase(lotLocation);
    }

    // Get all unoccupied parking spaces
    public List<Map<String, Object>> getUnoccupiedSpacesWithPricing() {
        List<ParkingSpace> unoccupiedSpaces = parkingSpaceRepository.findByIsOccupied(false);
        List<Map<String, Object>> spacesWithPricing = new ArrayList<>();

        for (ParkingSpace space : unoccupiedSpaces) {
            Map<String, Object> spaceDetails = new HashMap<>();
            spaceDetails.put("id", space.getParkingSpaceId());
            spaceDetails.put("spotNumber", space.getSpotNumber());
            spaceDetails.put("lotLocation", space.getLotLocation());

            // Resolve pricing
            Long pricingId;
            try {
                pricingId = Long.parseLong(space.getPricingRules());
            } catch (NumberFormatException e) {
                pricingId = null;
            }

            Double baseRate = 350.0; // Default price
            if (pricingId != null) {
                Optional<Pricing> pricing = pricingRepository.findById(pricingId);
                if (pricing.isPresent()) {
                    baseRate = pricing.get().getBaseRate();
                }
            }

            spaceDetails.put("pricing", baseRate);
            spacesWithPricing.add(spaceDetails);
        }

        return spacesWithPricing;
    }

}

