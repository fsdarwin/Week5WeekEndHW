package com.example.week5weekendhw.pojos;

import java.util.List;

public class Contact {

    String name;
    String address;

    public Contact() {
    }

    public Contact(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name ='" + name + '\'' +
                ", address =" + address + "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
