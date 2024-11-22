package org.example.lab5java.classes.controllers;

import org.example.lab5java.classes.servises.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // Загрузка файла
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            return "File uploaded successfully: " + fileName;
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    // Получение файла по имени
    @GetMapping("/files/{fileName}")
    public byte[] getFile(@PathVariable String fileName) {
        try {
            Path path = fileStorageService.getFilePath(fileName);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return new byte[0]; // Возвращаем пустой массив в случае ошибки
        }
    }
}