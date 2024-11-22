package com.project.parkingmngmtsystem.transaction;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import com.project.parkingmngmtsystem.parking_spot.ParkingSpaceRepository;
import com.project.parkingmngmtsystem.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }


    public void processPayment(TransactionRequest request) {
        System.out.println("Processing payment for space ID: " + request.getSpaceId());

        // Validate time range
        // Validate time range
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time.");
        }

        // Optional: Recalculate end time based on fixed duration (e.g., 3 hours)
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = startTime.plusHours(3); // Fixed duration

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setSpaceId(request.getSpaceId());
        transaction.setCarNumber(request.getCarNumber());
        transaction.setStartTime(startTime);
        transaction.setEndTime(endTime);
        transaction.setTotalCost(request.getTotalCost());
        transactionRepository.save(transaction);

        // Update parking space
        ParkingSpace parkingSpace = (ParkingSpace) parkingSpaceRepository.findBySpotNumber(request.getSpaceId())
                .orElseThrow(() -> new RuntimeException("Parking space not found for ID: " + request.getSpaceId()));

        parkingSpace.setOccupied(true);
        parkingSpaceRepository.save(parkingSpace);

        System.out.println("Payment processed successfully. Parking space " + parkingSpace.getParkingSpaceId() + " is now occupied.");
    }

    public Double getTotalAmount() {
        return transactionRepository.sumTotalCost();
    }

}
