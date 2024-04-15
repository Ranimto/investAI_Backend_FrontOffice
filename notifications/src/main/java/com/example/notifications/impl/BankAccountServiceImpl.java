package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Repository.BankAccountRepo;
import com.example.notifications.Services.BankAccountService;
import com.example.notifications.models.BankAccount;
import com.example.notifications.models.BankAccountResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.BANK_ACCOUNT_NOT_FOUND;
import static com.example.notifications.Exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepo bankAccountRepo;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    String ApplicationURL = "https://localhost:8443";

    public List<BankAccountDto> getAllBankAccFromFineract() {
        try {
            restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());
            String url = ApplicationURL + "/fineract-provider/api/v1/savingsaccounts";

            // Set the required header
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("mifos", "password");
            headers.set("Fineract-Platform-TenantId", "default");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the request with the specified headers
            ResponseEntity<BankAccountResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, BankAccountResponse.class);
            BankAccountResponse bankAccountDtos = response.getBody();
            log.info("the response", response.getBody());

            return new ArrayList<>(bankAccountDtos.getPageItems());
        } catch (HttpClientErrorException e) {
            log.info("the response", e);
            System.err.println("HTTP error when retrieving data : " + " - " + e.getStatusText());
            return new ArrayList<>();
        } catch (RestClientException e) {
            log.info("the response", e);
            System.err.println("RestClientException error during data recovery : " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public Optional<BankAccountDto> getBankAccFromFineractByAccountNo(String accountNo) {
        try {
            List<BankAccountDto> bankAccountDtoList = getAllBankAccFromFineract();

            // Filtrer la liste pour trouver le BankAccountDto avec l'accountNo donné
            Optional<BankAccountDto> optionalBankAccountDto = bankAccountDtoList.stream()
                    .filter(account -> account.getAccountNo().equals(accountNo))
                    .findFirst();

            return optionalBankAccountDto;
        } catch (Exception e) {
            log.error("Error while retrieving bank account by account number", e);
            return Optional.empty();
        }
    }



    public BankAccountDto findBankAccByIdFromFineract(Long id) {

        try {
            restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());
            String url = ApplicationURL + "/fineract-provider/api/v1/glaccounts/" + id;
            // Set the required header
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("mifos", "password");
            headers.set("Fineract-Platform-TenantId", "default");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<BankAccountDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, BankAccountDto.class);
            BankAccountDto bankAccountDto = response.getBody();
            log.info("the response", response.getBody());

            return bankAccountDto;

        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error when retrieving data : " + " - " + e.getStatusText());
            return null;
        } catch (RestClientException e) {
            System.err.println("RestClientException error during data recovery : " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


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
            bankAccount.setActive(bankAccountDto.isActive());
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

    public List<BankAccountDto> getBankAccountByInvestorId(Long userId) {
        List<BankAccount> bankAccounts = bankAccountRepo.findByUserId(userId);
        return bankAccounts.stream().map(u -> modelMapper.map(u, BankAccountDto.class)).collect(Collectors.toList());
    }

    public Optional<BankAccountDto> getUserActiveAccount(Long userId){
        if (userId==0) throw new EntityNotFoundException(USER_NOT_FOUND.getMessage());
        List<BankAccountDto> bankAccountDtoList= getBankAccountByInvestorId(userId);
        return   bankAccountDtoList.stream().filter(account-> account.isActive()).findFirst();

    }


}
