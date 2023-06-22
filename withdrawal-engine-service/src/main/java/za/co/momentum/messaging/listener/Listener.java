package za.co.momentum.messaging.listener;

import dto.WithdrawalEventStatusEnum;
import dto.WithdrawalStatusQueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import util.WithdrawalEngineMapperUtils;
import za.co.momentum.model.WithdrawalStatus;
import za.co.momentum.repo.WithdrawalStatusRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

@Component
public class Listener {
    Logger logger = LoggerFactory.getLogger(Listener.class);

    private final WithdrawalStatusRepository withdrawalStatusRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public Listener(WithdrawalStatusRepository withdrawalStatusRepository) {
        this.withdrawalStatusRepository = withdrawalStatusRepository;
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

        createWithdrawalStatus(queueMessage.getTrxId(),
                queueMessage.getWithdrawalEventStatusEnum());

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    private void createWithdrawalStatus(Long trxId, WithdrawalEventStatusEnum statusEnum) {
        WithdrawalStatus newStatus = new WithdrawalStatus();
        newStatus.setTransactionTime(Timestamp.valueOf(LocalDateTime.now()));
        newStatus.setWithdrawalTransactionId(trxId);
        newStatus.setStatus(statusEnum.ordinal());
        withdrawalStatusRepository.save(newStatus);
    }
}