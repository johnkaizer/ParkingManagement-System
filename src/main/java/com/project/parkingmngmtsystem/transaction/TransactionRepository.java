package com.project.parkingmngmtsystem.transaction;

import com.project.parkingmngmtsystem.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(t.totalCost), 0) FROM Transaction t")
    Double sumTotalCost();
}

