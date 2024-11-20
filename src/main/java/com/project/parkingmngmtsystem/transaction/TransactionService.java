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
        // Save transaction details
        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setSpaceId(request.getSpaceId());
        transaction.setCarNumber(request.getCarNumber());
        transaction.setStartTime(request.getStartTime());
        transaction.setEndTime(request.getEndTime());
        transaction.setTotalCost(request.getTotalCost());
        transactionRepository.save(transaction);

        // Update parking space status
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new RuntimeException("Parking space not found"));
        parkingSpace.setOccupied(true);
        parkingSpaceRepository.save(parkingSpace);
    }
}
