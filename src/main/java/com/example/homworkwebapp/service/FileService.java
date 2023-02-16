package com.example.homworkwebapp.service;

public interface FileService {

    boolean saveToFile(String json, String dataFileName);


    String readFromFile(String dataFileName);
}
