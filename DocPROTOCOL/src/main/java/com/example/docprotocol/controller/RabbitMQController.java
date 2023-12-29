package com.example.docprotocol.controller;

import com.example.docprotocol.services.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/protocol")
public class RabbitMQController {

    // Inject your service that interacts with RabbitMQ
    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping("/rabbitMQ")
    void test() {
        rabbitMQService.getFileUploadQueueStateSimple();
//        System.out.println(rabbitMQService.getFileUploadQueueStateSimple());
    }

//    public Map<String, Object> getFileUploadQueueState() {
//        return rabbitMQService.getFileUploadQueueState();
//    }
}
