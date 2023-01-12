package com.isa.control.filesFactory;

import java.util.Objects;

public class MyObject {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyObject myObject = (MyObject) o;
        return id == myObject.id && Objects.equals(name, myObject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
