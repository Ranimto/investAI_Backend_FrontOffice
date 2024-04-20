package com.example.notifications.impl;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.ClientDto;
import com.example.notifications.Repository.ClientRepo;
import com.example.notifications.models.BankAccount;
import com.example.notifications.models.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor

public class ClientServiceImpl {
    private  final ClientRepo clientRepo ;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate ;
    String ApplicationURL = "https://localhost:8443";



    public ClientDto getClientByIdFromFineract(Long clientId){
        try {
            restTemplate.setRequestFactory(new NoSSLValidationHttpRequestFactory());
            String url = ApplicationURL + "/fineract-provider/api/v1/clients/" + clientId;

            // Set the required header
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("mifos", "password");
            headers.set("Fineract-Platform-TenantId", "default");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ClientDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ClientDto.class);
            ClientDto clientDto = response.getBody();

            return clientDto;

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

    public ClientDto saveClient(ClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        Client savedClient = clientRepo.save(client);
        return modelMapper.map(savedClient, ClientDto.class);
    }

}
