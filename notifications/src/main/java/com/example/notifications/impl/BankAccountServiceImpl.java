package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Repository.BankAccountRepo;
import com.example.notifications.Services.BankAccountService;
import com.example.notifications.models.BankAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.BANK_ACCOUNT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepo bankAccountRepo;
    private final ModelMapper modelMapper;

    public List<BankAccountDto> getAllBankAcc() {
        List<BankAccount> bankAccounts = bankAccountRepo.findAll();
        return bankAccounts.stream().map(u -> modelMapper.map(u, BankAccountDto.class)).collect(Collectors.toList());
    }

    public Optional<BankAccountDto> findBankAccById(Long id) {
        Optional<BankAccount> bankAccount = bankAccountRepo.findById(id);
        return Optional.ofNullable(bankAccount.map(u -> modelMapper.map(u, BankAccountDto.class)).orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND.getMessage())));
    }

    public BankAccountDto addBankAccount(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);
        BankAccount savedBankAcc = bankAccountRepo.save(bankAccount);
        return modelMapper.map(savedBankAcc, BankAccountDto.class);
    }

    public BankAccountDto UpdateBankAccount(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = bankAccountRepo.findById(bankAccountDto.getId()).orElse(null);
        if (bankAccount != null) {

            bankAccount.setAccountNo(bankAccountDto.getAccountNo());
            bankAccount.setBankName(bankAccountDto.getBankName());
            bankAccount.setActive(bankAccountDto.isActive());
            bankAccount.setBalance(bankAccountDto.getBalance());
            bankAccount.setCurrency(bankAccountDto.getCurrency());

            BankAccount updatedBankAcc = bankAccountRepo.save(bankAccount);
            return modelMapper.map(updatedBankAcc, BankAccountDto.class);
        } else {
            return null;
        }
    }

    public void DeleteById(Long id) {
        if (id.equals(0)) log.error("BankAccountId is null");
        else {
            bankAccountRepo.deleteById(id);
        }
    }


}
