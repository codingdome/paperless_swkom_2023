package twentyseven.four.docservices.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twentyseven.four.docservices.service.DocumentProcessingService;

import java.util.logging.Logger;

@Component
public class FileMessageListener {

    private static final Logger logger = Logger.getLogger(String.valueOf(FileMessageListener.class));

    @Autowired
    private DocumentProcessingService documentProcessingService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(String fileName) throws Exception {

        logger.info("--- RECEIVED MESSAGE --- " + fileName);
        logger.info("--- START PROCESSING ---");
        documentProcessingService.start(fileName);
    }
}
