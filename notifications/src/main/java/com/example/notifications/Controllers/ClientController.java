package com.example.notifications.Controllers;

import com.example.notifications.Dto.BankAccountDto;
import com.example.notifications.Dto.ClientDto;
import com.example.notifications.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

private final ClientServiceImpl clientService ;

    @GetMapping("get/{clientId}")
    public ResponseEntity<ClientDto> getClientByIdFromFineract(@PathVariable Long clientId) {
        ClientDto  clientDto = clientService.getClientByIdFromFineract(clientId);
        if (clientDto!=null) {
            return ResponseEntity.ok(clientDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto clientDto) {
        ClientDto savedClient = clientService.saveClient(clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
}
