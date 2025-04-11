package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.AuthorizationResponse;
import com.atircio.pickpay.exceptions.TransactionFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionAuthorizationService {

    private final RestTemplate restTemplate;



        public TransactionAuthorizationService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public AuthorizationResponse verifyTransaction() {
            String url = "https://util.devi.tools/api/v2/authorize";

            try {
                ResponseEntity<AuthorizationResponse> response = restTemplate.getForEntity(url, AuthorizationResponse.class);
                return response.getBody(); // safe if 200 OK
            } catch (HttpClientErrorException.Forbidden ex) {
                // Parse 403 response manually
                ObjectMapper mapper = new ObjectMapper();
                try {
                    return mapper.readValue(ex.getResponseBodyAsString(), AuthorizationResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to parse authorization response", e);
                }
            }
        }
    }


