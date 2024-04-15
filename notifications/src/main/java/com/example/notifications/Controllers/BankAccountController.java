package com.example.notifications.Controllers;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.impl.BankAccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bankAccount")
@RequiredArgsConstructor
public class BankAccountController {
 private final BankAccountServiceImpl bankAccountServiceImpl;

    @GetMapping("find/{id}")
    public ResponseEntity<BankAccountDto> findBankAccountById(@PathVariable("id") Long id) {
        Optional<BankAccountDto> bankAccountDto = bankAccountServiceImpl.findBankAccById(id);
        if (bankAccountDto.isPresent()) {
            return new ResponseEntity<>(bankAccountDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("get/{accountNo}")
    public ResponseEntity<BankAccountDto> getBankAccountByAccountNo(@PathVariable String accountNo) {
        Optional<BankAccountDto> bankAccountDtoOptional = bankAccountServiceImpl.getBankAccFromFineractByAccountNo(accountNo);
        if (bankAccountDtoOptional.isPresent()) {
            return ResponseEntity.ok(bankAccountDtoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("findFromFineract/{id}")
    public ResponseEntity<BankAccountDto> findBankAccountByIdFromFineract(@PathVariable("id") Long id) {
       BankAccountDto bankAccountDto = bankAccountServiceImpl.findBankAccByIdFromFineract(id);
            return new ResponseEntity<>(bankAccountDto, HttpStatus.OK);
    }

    @GetMapping("/getAllFromFineract")
    public ResponseEntity<List<BankAccountDto>> getAllAccountsFromFineract() {
        List<BankAccountDto> bankAccounts = bankAccountServiceImpl.getAllBankAccFromFineract();
        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        List<BankAccountDto> bankAccounts = bankAccountServiceImpl.getAllBankAcc();
        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BankAccountDto> addBankAcc(@RequestBody BankAccountDto bankAccountDto) {
       BankAccountDto addedBankAccount = bankAccountServiceImpl.addBankAccount(bankAccountDto);
        return new ResponseEntity<>(addedBankAccount, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<BankAccountDto> updateBankAcc(@RequestBody BankAccountDto bankAccountDto) {
       BankAccountDto updatedBankAcc = bankAccountServiceImpl.UpdateBankAccount(bankAccountDto);
        return new ResponseEntity<>(updatedBankAcc, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteBankAcc(@PathVariable("id") Long id) {
        if (!bankAccountServiceImpl.findBankAccById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            bankAccountServiceImpl.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("getBankAccountByInvestor/{investorId}")
    public ResponseEntity<List<BankAccountDto>> getBankAccountsByInvestorId(@PathVariable Long investorId) {
        List<BankAccountDto> bankAccountsDto= bankAccountServiceImpl. getBankAccountByInvestorId(investorId);
        return ResponseEntity.ok(bankAccountsDto);
    }
    @GetMapping("getUserActiveAccount/{userId}")
    public  ResponseEntity<Optional<BankAccountDto>> getUserActiveAccount (@PathVariable Long userId){
        Optional<BankAccountDto> bankAccountDto=bankAccountServiceImpl.getUserActiveAccount(userId);
        return ResponseEntity.ok(bankAccountDto);
    }
}
