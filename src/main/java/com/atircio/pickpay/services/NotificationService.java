package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.NotificationSenderResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean notifyUser(String email, String message) {
        String url = "https://util.devi.tools/api/v1/notify";

        // Build the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("message", message);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap request body and headers
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(url, entity, NotificationSenderResponse.class);

        // Return true if 204 No Content
        return restTemplate.postForEntity(url, entity, NotificationSenderResponse.class).getStatusCode() == HttpStatus.NO_CONTENT;
    }



}
