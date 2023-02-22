package com.example.homworkwebapp.service;

import java.io.File;

public interface FileService {

    File saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    void clearFile();

    File getFile(String dataFileName);
}
