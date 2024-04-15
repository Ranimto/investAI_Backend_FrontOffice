package com.example.notifications.Controllers;


import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.RequestAccountDto;
import com.example.notifications.Exception.EntityNotFoundException;
import com.example.notifications.impl.RequestAccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requestAccount")
@RequiredArgsConstructor
public class RequestAccountController {
    private  final RequestAccountServiceImpl requestAccountService ;

    @PostMapping("/approveRequestAccount")
    public ResponseEntity<BankAccountDto> approveRequestAccount(@RequestBody RequestAccountDto requestAccountDto) {
        BankAccountDto isApproved = requestAccountService.approveRequestAccount(requestAccountDto);
        if (isApproved!=null) {
            return new ResponseEntity<>(isApproved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(isApproved, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bank-accounts/{accountNo}")
    public ResponseEntity<BankAccountDto> getBankAccountByAccountNo(@PathVariable String accountNo) {
        try {
            BankAccountDto bankAccountDto = requestAccountService.checkAccountExistence(accountNo);
            return ResponseEntity.ok(bankAccountDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
