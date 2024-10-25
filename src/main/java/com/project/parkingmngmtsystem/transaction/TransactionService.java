package com.project.parkingmngmtsystem.transaction;

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

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setUser(updatedTransaction.getUser());
        transaction.setParkingSpace(updatedTransaction.getParkingSpace());
        transaction.setStartTime(updatedTransaction.getStartTime());
        transaction.setEndTime(updatedTransaction.getEndTime());
        transaction.setTotalCost(updatedTransaction.getTotalCost());
        transaction.setPaymentStatus(updatedTransaction.getPaymentStatus());
        return transactionRepository.save(transaction);
    }
}
