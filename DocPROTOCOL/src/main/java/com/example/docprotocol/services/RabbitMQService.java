package com.example.docprotocol.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;

@Service
public class RabbitMQService {

    public Map<String, Object> getFileUploadQueueStateSimple() {
        String queueUrl = "http://localhost:9093/api/queues/%2F/fileUploadQueue";
        RestTemplate restTemplate = new RestTemplate();

        // Set up basic authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "12345678");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the request
        ResponseEntity<Map> response = restTemplate.exchange(queueUrl, HttpMethod.GET, entity, Map.class);

        // Return the body
        return response.getBody();
    }
}