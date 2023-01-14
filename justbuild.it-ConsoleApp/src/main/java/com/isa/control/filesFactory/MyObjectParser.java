package com.isa.control.filesFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.entity.Offer;

import java.io.IOException;

public class MyObjectParser {

    private final ObjectMapper objectMapper;

    public MyObjectParser() {
        objectMapper = new ObjectMapper();
    }

    public String serialize(Offer obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public Offer deserialize(String json) throws IOException {
        return objectMapper.readValue(json, Offer.class);
    }
}
