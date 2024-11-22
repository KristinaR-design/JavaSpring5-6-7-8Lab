package org.example.lab5java.classes.controllers;

import org.example.lab5java.classes.Users;
import org.example.lab5java.classes.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;



@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    // Путь для сохранения изображений (укажите свой путь)
    private static final String IMAGE_DIRECTORY = "profile_images/";
    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        Users currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/profile/update") // Изменение маршрута на уникальный
    public String updateProfile(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("profileImage") MultipartFile profileImage,
            Model model
    ) {
        // Получаем текущего пользователя
        Users user = userService.getCurrentUser(username);

        // Обновляем информацию пользователя
        user.setUsername(username);
        user.setEmail(email);

        // Обработка загрузки изображения
        if (!profileImage.isEmpty()) {
            try {
                // Проверяем директорию для хранения изображений
                File directory = new File(IMAGE_DIRECTORY);
                if (!directory.exists() && !directory.mkdirs()) {
                    logger.log(Level.SEVERE, "Не удалось создать директорию для изображений: " + IMAGE_DIRECTORY);
                    throw new IOException("Не удалось создать директорию для изображений");
                }

                // Создаем уникальное имя файла
                String fileName = user.getId() + "_" + profileImage.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]", "_");
                String filePath = IMAGE_DIRECTORY + fileName;

                // Сохраняем файл
                Files.write(Paths.get(filePath), profileImage.getBytes());

                // Сохраняем путь к изображению в базе данных
                user.setProfileImage(filePath);

            } catch (IOException e) {
                logger.log(Level.SEVERE, "Ошибка при сохранении файла профиля", e);
                e.printStackTrace();
                model.addAttribute("error", "Ошибка при загрузке изображения.");
                return "profile"; // Возвращаемся на страницу профиля при ошибке
            }
        }



        userService.saveUser(user);
        model.addAttribute("user", user);

        return "redirect:/profile";
    }
}