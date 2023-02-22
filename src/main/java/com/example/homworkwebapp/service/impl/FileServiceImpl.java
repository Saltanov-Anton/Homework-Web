package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Override
    public File saveToFile(String json, String dataFileName) {
        try {
            clearFile();
            return Files.writeString(Path.of(dataFilePath, dataFileName), json).toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readFromFile(String dataFileName) {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clearFile() {
        try {
            Files.delete(Path.of(dataFilePath));
            Files.createFile(Path.of(dataFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile(String dataFileName) {
        return new File(dataFilePath + "/" + dataFileName);
    }
}
