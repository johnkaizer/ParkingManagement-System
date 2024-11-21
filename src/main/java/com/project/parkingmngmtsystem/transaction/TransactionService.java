package com.project.parkingmngmtsystem.transaction;

import com.project.parkingmngmtsystem.parking_spot.ParkingSpace;
import com.project.parkingmngmtsystem.parking_spot.ParkingSpaceRepository;
import com.project.parkingmngmtsystem.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Log the incoming request for debugging
        System.out.println("Processing payment for space ID: " + request.getSpaceId());

        // Save transaction details
        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setSpaceId(request.getSpaceId());
        transaction.setCarNumber(request.getCarNumber());
        transaction.setStartTime(request.getStartTime());
        transaction.setEndTime(request.getEndTime());
        transaction.setTotalCost(request.getTotalCost());
        transactionRepository.save(transaction);

        // Fetch the parking space
        Optional<Object> parkingSpaceOpt = parkingSpaceRepository.findBySpotNumber(request.getSpaceId());
        if (parkingSpaceOpt.isEmpty()) {
            throw new RuntimeException("Parking space not found for ID: " + request.getSpaceId());
        }

        // Update parking space status
        ParkingSpace parkingSpace = (ParkingSpace) parkingSpaceOpt.get();
        parkingSpace.setOccupied(true);

        // Log the parking space update
        System.out.println("Updating parking space " + parkingSpace.getParkingSpaceId() + " to occupied.");

        parkingSpaceRepository.save(parkingSpace);
    }

    public Double getTotalAmount() {
        return transactionRepository.sumTotalCost();
    }

}
