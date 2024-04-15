package com.example.notifications.Controllers;

import com.example.notifications.Dto.TransactionDto;
import com.example.notifications.impl.TransactionServiceImpl;
import com.example.notifications.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto savedTransaction = transactionService.addTransaction(transactionDto);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (!transactionService.findTransactionById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            transactionService.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/buyTransaction")
    public ResponseEntity<Void> buyTransaction(TransactionDto transactionDto) {
        transactionService.buyTransaction(transactionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/sellTransaction")
    public ResponseEntity<Void> sellTransaction(TransactionDto transactionDto) {
        transactionService.sellTransaction(transactionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findTransactionByInvestorId/{id}")
    public ResponseEntity<List<Transaction>>  findTransactionByInvestorId(@PathVariable Long id ) {
        transactionService.findTransactionByInvestorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
