package com.example.notifications.Controllers;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.TransactionDto;
import com.example.notifications.impl.TransactionServiceImpl;
import com.example.notifications.models.Transaction;
import com.example.notifications.models.TransactionRequest;
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

    @PostMapping("/addTransaction")
    public ResponseEntity<TransactionDto> addTransactionToFineract(@RequestBody TransactionRequest request) {
        TransactionDto transactionDto = transactionService.addTransactionToFineract(request.getFromAccountNo(), request.getToAccountNo(), request.getAmount());
        if (transactionDto != null) {
            return ResponseEntity.ok(transactionDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/getAllFromFineract")
//    public ResponseEntity<List<TransactionDto>> getAllTransactionFromFineract() {
//        List<TransactionDto> transactionDtos = transactionService.getAllTransactionsFromFineract();
//        return new ResponseEntity<>(transactionDtos, HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto savedTransaction = transactionService.addTransaction(transactionDto);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        if (!transactionService.findTransactionById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            transactionService.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
//    @GetMapping("/buyTransaction")
//    public ResponseEntity<Void> buyTransaction(TransactionDto transactionDto) {
//        transactionService.buyTransaction(transactionDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @GetMapping("/sellTransaction")
//    public ResponseEntity<Void> sellTransaction(TransactionDto transactionDto) {
//        transactionService.sellTransaction(transactionDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/findTransactionByInvestorId/{id}")
    public ResponseEntity<List<Transaction>>  findTransactionByInvestorId(@PathVariable Integer id ) {
        transactionService.findTransactionByInvestorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
