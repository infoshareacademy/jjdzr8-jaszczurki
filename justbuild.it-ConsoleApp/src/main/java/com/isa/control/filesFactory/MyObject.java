package com.isa.control.filesFactory;


import java.util.Objects;

/*

     wszystko co ta klasa robiła przeniosłem do klasy Offer
     po moich zmianach klasa nie ma chyba dalszej racji bytu, ale zostawiam ją gdyby były inne pomysły

 */
public class MyObject {

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
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
