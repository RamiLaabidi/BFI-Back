package com.bfi.authentification.services;

import com.bfi.authentification.entities.User;
import com.bfi.authentification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class FileStorageService {

    @Value("${upload.directory}") // Read from application.properties
    private String uploadDir;


    @Autowired
    private UserRepository userRepository;

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;


    public String uploadImage(Long userId,MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Please provide a valid file.");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds the maximum limit of 2 MB.");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Filename contains invalid path sequence " + fileName);
            }

            // Build the path to save the file
            String filePath = uploadDir + fileName;

            // Transfer the file to the upload directory
            File destFile = new File(filePath);
            file.transferTo(destFile);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            user.setProfilePicture(fileName);

            // Save the user to the repository
            User savedUser = userRepository.save(user);

            if (savedUser != null) {
                return "File uploaded successfully: " + filePath;
            } else {
                return "Failed to save user or upload file";
            }
        } catch (IOException | IllegalArgumentException ex) {
            throw new IOException("Failed to upload file: " + ex.getMessage(), ex);
        }
    }


}