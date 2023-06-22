package za.co.momentum.messaging.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import util.WithdrawalEngineMapperUtils;
import dto.WithdrawalStatusQueueMessage;
import za.co.momentum.service.WithdrawalEngineService;

import java.util.concurrent.CountDownLatch;

@Component
public class Listener {
    Logger logger = LoggerFactory.getLogger(Listener.class);

    private final WithdrawalEngineService withdrawalEngineService;

    private final CountDownLatch latch = new CountDownLatch(1);

    public Listener(WithdrawalEngineService withdrawalEngineService) {
        this.withdrawalEngineService = withdrawalEngineService;
    }

    public void handleEvent(String message) {
        logger.info("Received withdrawal status message", message);
        WithdrawalStatusQueueMessage queueMessage;
        try {
            queueMessage = WithdrawalEngineMapperUtils.jsonStringToObject(message,
                    WithdrawalStatusQueueMessage.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        withdrawalEngineService.createWithdrawalStatus(queueMessage.getTrxId(),
                queueMessage.getWithdrawalEventStatusEnum());

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}