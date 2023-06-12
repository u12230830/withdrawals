package za.co.momentum.service;

import org.springframework.stereotype.Service;

@Service
public interface EventBridgeService {
    void pushEvent();
}
