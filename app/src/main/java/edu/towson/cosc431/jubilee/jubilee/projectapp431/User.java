package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import java.util.UUID;

/**
 * Created by Montrell on 5/10/2018.
 */

public class User {
    public String fName;
    public String lName;
    public String email;
    public String address;
    public String city;
    public String state;
    public UUID id;
    public int userId;

    public User() {
        id = UUID.randomUUID();
    }

    public User(String fName, String lName, String email, String address, String city, String state) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;

        id = UUID.randomUUID();
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getState() { return state; }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setfName(String firstName) {
        this.fName = firstName;
    }

    public void setlName(String lastName) {
        this.lName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return "User{" +
                "first name='" + fName + '\'' +
                ", last name='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", city=" + city +
                ", id=" + userId +
                '}';
    }
}