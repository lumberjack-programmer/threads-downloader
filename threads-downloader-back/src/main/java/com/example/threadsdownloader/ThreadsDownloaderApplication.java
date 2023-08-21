package com.example.threadsdownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThreadsDownloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadsDownloaderApplication.class, args);
    }

}
