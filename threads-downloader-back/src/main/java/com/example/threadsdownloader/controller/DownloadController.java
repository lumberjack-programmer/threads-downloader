package com.example.threadsdownloader.controller;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("/files")
public class DownloadController {

    @GetMapping("/videos/{fileName}")
    public ResponseEntity<?> downloadVideoFile(@PathVariable String fileName) throws IOException {
        Path pathDir = Paths.get("files/videos/" + fileName);

        if (!Files.exists(pathDir)) {
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> filePaths = Files.walk(pathDir)) {
            Optional<Path> result = filePaths.filter(f -> f.getFileName().toString().equals(fileName)).findFirst();

            if (result.isPresent()) {
                Path filePath = result.get();
                UrlResource resource = new UrlResource(filePath.toUri());

                // Set headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentLength(resource.contentLength());
                headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }


    @GetMapping("/images/{fileName}")
    public ResponseEntity<?> downloadImageFile(@PathVariable String fileName) throws IOException {
        Path pathDir = Paths.get("files/images/" + fileName);

        if (!Files.exists(pathDir)) {
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> filePaths = Files.walk(pathDir)) {
            Optional<Path> result = filePaths.filter(f -> f.getFileName().toString().equals(fileName)).findFirst();

            if (result.isPresent()) {
                Path filePath = result.get();
                UrlResource resource = new UrlResource(filePath.toUri());

                // Set headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentLength(resource.contentLength());
                headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
}
