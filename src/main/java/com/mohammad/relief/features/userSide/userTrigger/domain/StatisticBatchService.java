package com.mohammad.relief.features.userSide.userTrigger.domain;

import com.mohammad.relief.features.adminSide.globalTrigger.data.GlobalTriggerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticBatchService {

    @Autowired
    private GlobalTriggerRepository globalTriggerRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void runDailySync() {
        globalTriggerRepository.refreshAllAdoptionCounts();
    }
}