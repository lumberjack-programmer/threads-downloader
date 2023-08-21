package com.example.threadsdownloader.cronjob;

import com.example.threadsdownloader.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RemoveFilesCronJob {

    private final StorageService storageService;

    @Autowired
    public RemoveFilesCronJob(StorageService storageService) {
        this.storageService = storageService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void removeOldFiles() throws IOException {
        storageService.removeFiles();
    }
}
