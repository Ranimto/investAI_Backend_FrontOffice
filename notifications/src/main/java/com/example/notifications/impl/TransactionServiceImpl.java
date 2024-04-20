package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.TransactionDto;
import com.example.notifications.Repository.TransactionRepo;
import com.example.notifications.Services.TransactionService;
import com.example.notifications.models.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
   private final ClientServiceImpl clientService;
    private final RestTemplate restTemplate;
    String ApplicationURL = "https://localhost:8443";


//    public List<TransactionDto> getAllTransactionsFromFineract() {
//        try {
//            restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());
//            String url = ApplicationURL + "/fineract-provider/api/v1/accounttransfers";
//
//            // Set the required header
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBasicAuth("mifos", "password");
//            headers.set("Fineract-Platform-TenantId", "default");
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            // Make the request with the specified headers
//            ResponseEntity<TransactionResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TransactionResponse.class);
//            TransactionResponse transactionResponse = response.getBody();
//            log.info("the response", response.getBody());
//
//            return new ArrayList<>(transactionResponse.getPageItems());
//        } catch (HttpClientErrorException e) {
//            log.info("the response", e);
//            System.err.println("HTTP error when retrieving data : " + " - " + e.getStatusText());
//            return new ArrayList<>();
//        } catch (RestClientException e) {
//            log.info("the response", e);
//            System.err.println("RestClientException error during data recovery : " + e.getMessage());
//            return new ArrayList<>();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }


    public TransactionDto addTransactionToFineract(String fromAccountNo, String toAccountNo, double amount) {
        try {
            TransactionDto transactionDto = new TransactionDto();

            // Obtenir la date actuelle formatée
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            Optional<BankAccountDto> fromBankAccount = bankAccountService.getBankAccFromFineractByAccountNo(fromAccountNo);
            Optional<BankAccountDto> toBankAccount = bankAccountService.getBankAccFromFineractByAccountNo(toAccountNo);

            log.info("fromBankAccount",fromBankAccount);
            log.info("toBankAccount",toBankAccount);


            if (fromBankAccount.isPresent() && toBankAccount.isPresent()) {

                transactionDto.setFromAccountId(fromBankAccount.get().getId());
                transactionDto.setFromAccountType(2);
                transactionDto.setFromClientId(fromBankAccount.get().getClientId());

                transactionDto.setDateFormat("dd MMMM yyyy");
                transactionDto.setTransferDescription("Transfer from bank Account " + fromAccountNo + " to bank Account " + toAccountNo);
                transactionDto.setTransferAmount(amount);
                transactionDto.setLocale("en");
                transactionDto.setTransferDate(formattedDate);

                transactionDto.setToAccountId(toBankAccount.get().getId());
                transactionDto.setToAccountType(2);
                transactionDto.setToClientId(toBankAccount.get().getClientId());

                Long fromOfficeId = clientService.getClientByIdFromFineract(fromBankAccount.get().getClientId()).getOfficeId();
                Long toOfficeId = clientService.getClientByIdFromFineract(toBankAccount.get().getClientId()).getOfficeId();

                transactionDto.setFromOfficeId(fromOfficeId);
                transactionDto.setToOfficeId(toOfficeId);
                log.info("transactionnn",transactionDto);

                restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());
                String url = ApplicationURL + "/fineract-provider/api/v1/accounttransfers";

                // Set the required header
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth("mifos", "password");
                headers.set("Fineract-Platform-TenantId", "default");
                HttpEntity<TransactionDto> requestEntity = new HttpEntity<>(transactionDto, headers);

                // Make the request with the specified headers
                ResponseEntity<TransactionDto> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, TransactionDto.class);

                // Save transaction in database

                Transaction transaction = new Transaction() ;

                transaction.setFromAccountId(transactionDto.getFromAccountId());
                transaction.setFromClientId(transactionDto.getFromClientId());
                transaction.setFromAccountType(transactionDto.getFromAccountType());
                transaction.setFromOfficeId(transactionDto.getFromOfficeId());


                transaction.setToAccountId(transactionDto.getToAccountId());
                transaction.setToClientId(transactionDto.getToClientId());
                transaction.setToAccountType(transactionDto.getToAccountType());
                transaction.setToOfficeId(transactionDto.getToOfficeId());

                transaction.setDateFormat(transactionDto.getDateFormat());
                transaction.setLocale(transactionDto.getLocale());
                transaction.setTransferAmount(transactionDto.getTransferAmount());
                transaction.setTransferDescription(transactionDto.getTransferDescription());
                transaction.setTransferDate(transactionDto.getTransferDate());


                return transactionDto;
            } else {
                throw new EntityNotFoundException("Invalid Account Number");
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP error when posting data", e);
            System.err.println("HTTP error when posting data : " + e.getMessage());
            return null;
        } catch (RestClientException e) {
            log.error("RestClientException error during data recovery", e);
            System.err.println("RestClientException error during data recovery : " + e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("An error occurred", e);
            e.printStackTrace();
            return null;
        }
    }




    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return transactions.stream()
                .map(u -> modelMapper.map(u, TransactionDto.class))
                .collect(Collectors.toList());
    }



    public Optional<TransactionDto> findTransactionById(Integer id) {
        Optional<Transaction> transaction = transactionRepo.findById(id);
        return Optional.ofNullable(transaction.map(u -> modelMapper.map(u, TransactionDto.class)).orElseThrow(() -> new EntityNotFoundException("transaction " + id + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public Optional<TransactionDto> findTransactionByInvestorId(Integer userId) {
        Optional<List<Transaction>> transaction = transactionRepo.findTransactionsByUserId(userId);
        return Optional.ofNullable(transaction.map(u -> modelMapper.map(u, TransactionDto.class)).orElseThrow(() -> new EntityNotFoundException("transaction " + userId + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public TransactionDto addTransaction(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        Transaction savedTansaction = transactionRepo.save(transaction);
        return modelMapper.map(savedTansaction, TransactionDto.class);
    }
//    public  void buyTransaction(TransactionDto transactionDto){
//        if (transactionDto.getType() == Type.BUY){
//            double RIB =transactionDto.getRIB();
//            Optional<BankAccountDto> bankAccountDto = bankAccountService.findBankAccById(transactionDto.getBankAccountId());
//            CompanyDto companyDto=companyService.getCompanyByRIB(RIB);
//            companyDto.setBalance(companyDto.getBalance()+ transactionDto.getAmount());
//          //  bankAccountDto.get().setAccountBalance(bankAccountDto.get().getAccountBalance()-transactionDto.getAmount());
//        }
//    }
//
//    public  void sellTransaction(TransactionDto transactionDto){
//        if (transactionDto.getType() == Type.SELL){
//            double RIB =transactionDto.getRIB();
//            Optional<BankAccountDto> bankAccountDto = bankAccountService.findBankAccById(transactionDto.getBankAccountId());
//            CompanyDto companyDto=companyService.getCompanyByRIB(RIB);
//            companyDto.setBalance(companyDto.getBalance()-transactionDto.getAmount());
//           // bankAccountDto.get().setAccountBalance(bankAccountDto.get().getAccountBalance()+transactionDto.getAmount());
//        }
//    }

    public void DeleteById(Integer id) {
        if (id.equals(0)) log.error("TransactionId is null");
        else
            transactionRepo.deleteById(id);
    }

}
