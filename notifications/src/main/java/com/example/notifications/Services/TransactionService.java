package com.example.notifications.Services;


import com.example.notifications.Dto.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
     List<TransactionDto> getAllTransactions();
     Optional<TransactionDto> findTransactionById(Long id);
     TransactionDto addTransaction(TransactionDto transactionDto);
    void DeleteById(Long id);
}
