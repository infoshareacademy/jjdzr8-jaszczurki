package com.isa.control.filesFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MyObjectParser {
    private final ObjectMapper objectMapper;
    public MyObjectParser() {
        objectMapper = new ObjectMapper();
    }

    public String serialize(MyObject obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public MyObject deserialize(String json) throws IOException {
        return objectMapper.readValue(json, MyObject.class);
    }
}