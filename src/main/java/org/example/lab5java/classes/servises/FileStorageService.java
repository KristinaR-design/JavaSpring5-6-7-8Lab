package org.example.lab5java.classes.servises;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {


    @Value("${C:/Users/User/OneDrive/Рабочий стол/Предметы муит/Java/JavaEE/Foto}")
    private String uploadDir;


    public FileStorageService() {

    }

    // Метод для сохранения файла
    public String storeFile(MultipartFile file) throws IOException {
        // Генерация уникального имени для файла
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Создаем путь для сохранения
        Path path = Paths.get(uploadDir, fileName);

        // Сохраняем файл
        Files.copy(file.getInputStream(), path);

        return fileName; // Возвращаем имя файла
    }

    // Метод для получения пути файла
    public Path getFilePath(String fileName) {
        return Paths.get(uploadDir, fileName);
    }

    // Метод для удаления файла
    public boolean deleteFile(String fileName) {
        Path filePath = Paths.get(uploadDir, fileName);
        File file = filePath.toFile();
        return file.delete(); // Удаляем файл
    }

    // Метод для получения содержимого директории с файлами
    public File[] listFiles() {
        File folder = new File(uploadDir);
        return folder.listFiles(); // Возвращаем все файлы в директории
    }
}