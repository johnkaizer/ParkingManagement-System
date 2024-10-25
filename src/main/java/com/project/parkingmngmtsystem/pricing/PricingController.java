package com.project.parkingmngmtsystem.pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping
    public List<Pricing> getAllPricing() {
        return pricingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pricing> getPricingById(@PathVariable Long id) {
        return pricingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pricing createPricing(@RequestBody Pricing pricing) {
        return pricingService.save(pricing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pricing> updatePricing(@PathVariable Long id, @RequestBody Pricing pricingDetails) {
        try {
            Pricing updatedPricing = pricingService.updatePricing(id, pricingDetails);
            return ResponseEntity.ok(updatedPricing);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePricing(@PathVariable Long id) {
        pricingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

