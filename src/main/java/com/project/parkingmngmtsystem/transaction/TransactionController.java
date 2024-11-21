package com.project.parkingmngmtsystem.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payForParking(@RequestBody TransactionRequest transactionRequest) {
        try {
            transactionService.processPayment(transactionRequest);
            return ResponseEntity.ok("Payment successful, spot marked as occupied.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Payment failed: " + e.getMessage());
        }
    }

    // New Endpoint to get the total amount from all transactions
    @GetMapping("/total-amount")
    public ResponseEntity<Double> getTotalAmount() {
        Double totalAmount = transactionService.getTotalAmount();
        return ResponseEntity.ok(totalAmount);
    }
}
