package com.example.homworkwebapp.service;

public interface FileService {

    void saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    void clearFile();
}
