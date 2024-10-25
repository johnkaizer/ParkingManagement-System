package com.project.parkingmngmtsystem.pricing;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    public List<Pricing> findAll() {
        return pricingRepository.findAll();
    }

    public Optional<Pricing> findById(Long id) {
        return pricingRepository.findById(id);
    }

    public Pricing save(Pricing pricing) {
        return pricingRepository.save(pricing);
    }

    public void deleteById(Long id) {
        pricingRepository.deleteById(id);
    }

    public Pricing updatePricing(Long id, Pricing updatedPricing) {
        Pricing pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing record not found"));
        pricing.setTimeRange(updatedPricing.getTimeRange());
        pricing.setBaseRate(updatedPricing.getBaseRate());
        pricing.setDemandFactor(updatedPricing.getDemandFactor());
        pricing.setDayType(updatedPricing.getDayType());
        pricing.setSeasonalAdjustment(updatedPricing.getSeasonalAdjustment());
        return pricingRepository.save(pricing);
    }
}


