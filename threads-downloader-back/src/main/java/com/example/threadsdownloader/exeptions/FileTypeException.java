package com.example.threadsdownloader.exeptions;

public class FileTypeException extends RuntimeException {
   public FileTypeException(String message) {
        super(message);
    }
}
