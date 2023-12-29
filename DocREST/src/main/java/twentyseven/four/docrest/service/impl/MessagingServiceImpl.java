package twentyseven.four.docrest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docrest.service.MessagingService;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {

    private static final Logger logger = Logger.getLogger(MessagingServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    @Override
    public void send(String message) {

        // LOGGING
        logger.info("--- SENDING --- " + message);

        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
