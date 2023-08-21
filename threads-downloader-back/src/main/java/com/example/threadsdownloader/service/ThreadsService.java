package com.example.threadsdownloader.service;

import com.example.threadsdownloader.model.FileType;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThreadsService {

    private final StorageService storageService;

    @Autowired
    public ThreadsService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Map<String, List<String>> downloadImagesAndVideos(String postUrl) {

        Map<String, List<String>> responseMap = new HashMap<>();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();

            page.navigate(postUrl);

            // Wait for the container element to be present
            page.waitForSelector("div[data-pressable-container=true]");

            List<ElementHandle> elementHandles = page.querySelectorAll("div[data-pressable-container=true]");

            List<String> imageUrls = new ArrayList<>();
            List<String> videoUrls = new ArrayList<>();

            elementHandles.forEach(elementHandle -> {
                List<ElementHandle> imgElements = elementHandle.querySelectorAll("img[draggable=false]");
                List<ElementHandle> videoElements = elementHandle.querySelectorAll("video");

                // Extract src attributes of img elements
                for (ElementHandle imgElement : imgElements) {
                    String imageUrl = imgElement.getAttribute("src");

                    try {
                        String fileUrl = storageService.downloadImagesAndVideos(imageUrl, FileType.IMAGE);
                        imageUrls.add(fileUrl);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Image Src: " + imageUrl);
                }

                // Extract src attributes of video elements
                for (ElementHandle videoElement : videoElements) {
                    String videoUrl = videoElement.getAttribute("src");

                    try {
                        String fileUrl = storageService.downloadImagesAndVideos(videoUrl, FileType.VIDEO);
                        videoUrls.add(fileUrl);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Video Src: " + videoUrl);
                }

            });
            browser.close();

            responseMap.put("imageUrls", imageUrls);
            responseMap.put("videoUrls", videoUrls);
        }


        return responseMap;
    }
}
