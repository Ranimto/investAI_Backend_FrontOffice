package com.example.notifications.Repository;

import com.example.notifications.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Integer> {

    @Transactional
    @Query("SELECT t FROM Transaction t JOIN t.bankAccount b JOIN b.user u WHERE u.id = :userId")
    Optional<List<Transaction>> findTransactionsByUserId(Integer userId);
}
