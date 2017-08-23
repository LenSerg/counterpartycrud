package com.example.sergey.counterpartycrud.entities;

import java.io.Serializable;

/**
 * Created by sergey on 20.08.17.
 */

public class Counterparty implements Serializable {
    private int id;
    private String photo;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String description;

    public Counterparty() { }

    public Counterparty(String photo, String name, String address, String phone,
                        String email, String website, String description) {
        this.photo = photo;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.description = description;
    }

    public Counterparty(int id, String photo, String name, String address, String phone,
                        String email, String website, String description) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
