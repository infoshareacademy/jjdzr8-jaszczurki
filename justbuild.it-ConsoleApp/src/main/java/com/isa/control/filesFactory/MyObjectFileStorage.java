package com.isa.control.filesFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.entity.Offer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyObjectFileStorage {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveToFile(List<Offer> offers, String filePath) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), offers);
    }

    public List<Offer> readFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {
        });
    }
}
