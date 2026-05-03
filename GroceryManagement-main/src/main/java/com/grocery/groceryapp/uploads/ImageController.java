package com.grocery.groceryapp.uploads;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
    @RestController
    @CrossOrigin
    public class ImageController {

        private static final String UPLOAD_DIR = "uploads/";

        @PostMapping("/upload")
        public String uploadImage(@RequestParam("file") MultipartFile file) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);

                Files.write(path, file.getBytes());

                return fileName; // return only name
            } catch (Exception e) {
                return "Error";
            }
        }
    }
