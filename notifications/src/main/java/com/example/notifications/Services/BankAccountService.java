package com.example.notifications.Services;

import com.example.notifications.Dto.BankAccountDto;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    List<BankAccountDto> getAllBankAccFromFineract();

    Optional<BankAccountDto> getBankAccFromFineractByAccountNo(String accountNo);

    BankAccountDto findBankAccByIdFromFineract(Long id);

    List<BankAccountDto> getAllBankAcc();

    Optional<BankAccountDto> findBankAccById(Long id);

    BankAccountDto addBankAccount(BankAccountDto bankAccountDto);

    BankAccountDto UpdateBankAccount(BankAccountDto bankAccountDto);

    void DeleteById(Long id);
}
