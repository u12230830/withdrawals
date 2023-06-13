package za.co.momentum.service;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

public class EventBridgeServiceImpl implements EventBridgeService {

    @Autowired
    EventBridgeClient eventBridgeClient;

    @Override
    public void pushEvent() {
        // todo: Do
    }
}
