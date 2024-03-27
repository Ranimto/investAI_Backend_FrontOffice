package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.CompanyDto;
import com.example.notifications.Dto.TransactionDto;
import com.example.notifications.Repository.TransactionRepo;
import com.example.notifications.Services.TransactionService;
import com.example.notifications.models.Transaction;
import com.example.notifications.models.Type;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.PROFILE_DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final ModelMapper modelMapper;
   private  final CompanyServiceImpl companyService;
   private final BankAccountServiceImpl bankAccountService ;

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return transactions.stream()
                .map(u -> modelMapper.map(u, TransactionDto.class))
                .collect(Collectors.toList());
    }

    public Optional<TransactionDto> findTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepo.findById(id);
        return Optional.ofNullable(transaction.map(u -> modelMapper.map(u, TransactionDto.class)).orElseThrow(() -> new EntityNotFoundException("transaction " + id + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public Optional<TransactionDto> findTransactionByInvestorId(Long userId) {
        Optional<List<Transaction>> transaction = transactionRepo.findTransactionsByUserId(userId);
        return Optional.ofNullable(transaction.map(u -> modelMapper.map(u, TransactionDto.class)).orElseThrow(() -> new EntityNotFoundException("transaction " + userId + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public TransactionDto addTransaction(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        Transaction savedTansaction = transactionRepo.save(transaction);
        return modelMapper.map(savedTansaction, TransactionDto.class);
    }
    public  void buyTransaction(TransactionDto transactionDto){
        if (transactionDto.getType() == Type.BUY){
            double RIB =transactionDto.getRIB();
            Optional<BankAccountDto> bankAccountDto = bankAccountService.findBankAccById(transactionDto.getBankAccountId());
            CompanyDto companyDto=companyService.getCompanyByRIB(RIB);
            companyDto.setBalance(companyDto.getBalance()+ transactionDto.getAmount());
            bankAccountDto.get().setBalance(bankAccountDto.get().getBalance()-transactionDto.getAmount());
        }
    }

    public  void sellTransaction(TransactionDto transactionDto){
        if (transactionDto.getType() == Type.SELL){
            double RIB =transactionDto.getRIB();
            Optional<BankAccountDto> bankAccountDto = bankAccountService.findBankAccById(transactionDto.getBankAccountId());
            CompanyDto companyDto=companyService.getCompanyByRIB(RIB);
            companyDto.setBalance(companyDto.getBalance()-transactionDto.getAmount());
            bankAccountDto.get().setBalance(bankAccountDto.get().getBalance()+transactionDto.getAmount());
        }
    }

    public void DeleteById(Long id) {
        if (id.equals(0)) log.error("TransactionId is null");
        else
            transactionRepo.deleteById(id);
    }

}
