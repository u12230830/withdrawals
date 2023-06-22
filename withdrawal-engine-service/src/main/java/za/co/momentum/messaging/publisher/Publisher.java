package za.co.momentum.messaging.publisher;

import dto.WithdrawalEventStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.WithdrawalEngineMapperUtils;
import za.co.momentum.config.WithdrawalServiceConfig;
import dto.WithdrawalStatusQueueMessage;
import za.co.momentum.messaging.listener.Listener;

import java.util.concurrent.TimeUnit;

@Component
public class Publisher {
    Logger logger = LoggerFactory.getLogger(Publisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final Listener listener;

    @Autowired
    public Publisher(Listener listener, RabbitTemplate rabbitTemplate) {
        this.listener = listener;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(WithdrawalEventStatusEnum statusEnum, Long trxId) throws AmqpException, InterruptedException {
        logger.info("Creating new withdrawal status and sending to queue: q.w.statuses");

        WithdrawalStatusQueueMessage withdrawalStatusQueueMessage = new WithdrawalStatusQueueMessage(statusEnum, trxId);

        rabbitTemplate.convertAndSend(WithdrawalServiceConfig.topicExchangeName, "q.w.statuses",
                WithdrawalEngineMapperUtils.objectToJsonString(withdrawalStatusQueueMessage));
        listener.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}