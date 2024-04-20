package com.example.notifications.Services;


import com.example.notifications.Dto.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

     TransactionDto addTransactionToFineract(String fromAccountNo, String toAccountNo, double amount);
     List<TransactionDto> getAllTransactions();
     Optional<TransactionDto> findTransactionById(Integer id);
     TransactionDto addTransaction(TransactionDto transactionDto);
    void DeleteById(Integer id);
}
