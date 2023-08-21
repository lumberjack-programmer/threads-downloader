package com.example.threadsdownloader.controller;

import com.example.threadsdownloader.service.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
public class ThreadsController {

    private final ThreadsService threadsService;

    @Autowired
    public ThreadsController(ThreadsService threadsService) {
        this.threadsService = threadsService;
    }

    @GetMapping("/post/getAll")
    public ResponseEntity<?> getImagesAndVideos(@RequestParam("url") String postUrl) {
//        boolean isCorrectPostPath = UrlUtils.checkPathUrl(postUrl);
//
//        if (!isCorrectPostPath) {
//            Map<String, String> error = new HashMap<>();
//            error.put("message", "Post url is incorrect!");
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//        }
        Map<String, List<String>> stringListMap = threadsService.downloadImagesAndVideos(postUrl);
        return new ResponseEntity<>(stringListMap, HttpStatus.OK);
    }
}
