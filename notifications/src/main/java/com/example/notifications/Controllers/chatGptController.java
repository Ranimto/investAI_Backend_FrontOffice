package com.example.notifications.Controllers;

import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/bot")
public class chatGptController {
        @PostMapping("/send")
        public ResponseEntity<String>sendMessage(@RequestBody String message)throws IOException{
            String apiEndpoint= "https://api.openai.com/v1/chat/completions";
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            System.out.println("HEEEEREEEEEE===");
            headers.set("Authorization", "Bearer "+ "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");
            headers.set("Access-Control-Allow-Origin","*");
            RestTemplate restTemplate=new RestTemplate();
            HttpEntity<String> request=new HttpEntity<>(message, headers);
            ResponseEntity<String> response= restTemplate.exchange (apiEndpoint, HttpMethod. POST, request, String.class);
            System.out.println(response.getBody());
            return response ;
        }
}
