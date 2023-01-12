package com.isa.control.filesFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MyObjectFileStorage {

    private final MyObjectParser parser;

    public MyObjectFileStorage(MyObjectParser parser) {
        this.parser = parser;
    }

    public void saveToFile(List<MyObject> objects, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (MyObject obj : objects) {
                String json = parser.serialize(obj);
                writer.write(json);
                writer.newLine();
            }
        }
    }

    public List<MyObject> readFromFile(String filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return reader.lines().map(line -> {
                        try {
                            return parser.deserialize(line);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
    }
}