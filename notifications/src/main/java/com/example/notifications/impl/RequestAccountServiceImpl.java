package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.ClientDto;
import com.example.notifications.Dto.RequestAccountDto;
import com.example.notifications.Repository.RequestAccountRepo;
import com.example.notifications.models.RequestAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.notifications.Exception.ErrorCode.BANK_ACCOUNT_NOT_FOUND;
import static com.example.notifications.Exception.ErrorCode.REQUEST_ACCOUNT_ID_IS_NULL;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestAccountServiceImpl {
    private  final RequestAccountRepo requestAccountRepo ;
    private final BankAccountServiceImpl bankAccountService ;
    private  final  ClientServiceImpl clientService ;
    private  final ModelMapper modelMapper ;
    private final RestTemplate restTemplate;
    String ApplicationURL = "https://localhost:8443";



    public BankAccountDto approveRequestAccount(RequestAccountDto requestAccountDto) {
        if (requestAccountDto == null)  {
            throw new EntityNotFoundException(REQUEST_ACCOUNT_ID_IS_NULL.getMessage());
        }
        String accountNo = requestAccountDto.getAccountNo();
        log.info("accountNo",accountNo);
        if (accountNo != null) {
            BankAccountDto bankAccountDto = checkAccountExistence(accountNo);
            if (bankAccountDto != null) {

                //save Client
                Long clientId =bankAccountDto.getClientId();
                ClientDto clientDto=clientService.getClientByIdFromFineract(clientId);
                clientService.saveClient(clientDto);

                //save BankAccount
                bankAccountDto.setUserId(requestAccountDto.getUserId());
                bankAccountService.addBankAccount(bankAccountDto);
                requestAccountDto.setStatus("Approved");
                RequestAccount requestAccount = modelMapper.map(requestAccountDto, RequestAccount.class);
                requestAccountRepo.save(requestAccount);
                return bankAccountDto;
            } else {
                requestAccountDto.setStatus("Rejected");
                RequestAccount requestAccount = modelMapper.map(requestAccountDto, RequestAccount.class);
                requestAccountRepo.save(requestAccount);
                throw new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND.getMessage());
            }

        } else {
            throw new EntityNotFoundException(REQUEST_ACCOUNT_ID_IS_NULL.getMessage());
        }
    }


    public BankAccountDto checkAccountExistence(String accountNo) {

        // Retrieve bank account DTO by account number
        Optional<BankAccountDto> optionalBankAccountDto = bankAccountService.getBankAccFromFineractByAccountNo(accountNo);
        restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());

        if (optionalBankAccountDto.isPresent()) {
            // Bank account DTO found, return it
            return optionalBankAccountDto.get();
        } else {
            // Bank account DTO not found, throw EntityNotFoundException
            throw new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND.getMessage());
        }
    }

}
