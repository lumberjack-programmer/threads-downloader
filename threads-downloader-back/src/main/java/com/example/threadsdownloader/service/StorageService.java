package com.example.threadsdownloader.service;

import com.example.threadsdownloader.exeptions.FileTypeException;
import com.example.threadsdownloader.model.FileType;
import com.example.threadsdownloader.utils.UrlUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final String BASE_URL = "http://localhost:8080/";

    public String downloadImagesAndVideos(String fileUrl, FileType fileType) throws IOException {
        String fileName = UrlUtils.extractFileNameFromUrl(fileUrl);
        URL url = new URL(fileUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = httpURLConnection.getInputStream()) {
                String relativePath;
                String directoriesPath;

                if (fileType == FileType.IMAGE) {
                    directoriesPath = "files/images";
                } else if (fileType == FileType.VIDEO) {
                    directoriesPath = "files/videos";
                } else {
                    throw new FileTypeException("File type no found!");
                }

                File directories = new File(directoriesPath);
                directories.mkdirs();

                File outputFile = new File(directories, fileName);

                try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    int bytesRead;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                System.out.println("File downloaded successfully.");

                return BASE_URL + directoriesPath + File.separator + fileName;
            }
        } else {
            System.out.println("Failed to download file. Response code: " + responseCode);
            return null;
        }
    }

    public void removeFiles() throws IOException {
        Path directoriesPath = Paths.get("files/");

        if (Files.exists(directoriesPath)) {
            try (Stream<Path> walk = Files.walk(directoriesPath)) {
                long currentTimeMillis = System.currentTimeMillis();
                long timeThreshold = TimeUnit.HOURS.toHours(2);

                walk.filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                FileTime fileTime = Files.readAttributes(path, BasicFileAttributes.class).creationTime();
                                long fileAgeMillis = currentTimeMillis - fileTime.toMillis();

                                if (fileAgeMillis > timeThreshold) {
                                    Files.delete(path);
                                    System.out.println("Deleted file: " + path);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        }
    }
}
