package com.p2j.MohamedApp.Model;

public class User {

    private String cin;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String img;
    private int role;
    private String bluetoothMac;


    public User() {
    }

    public User(String cin,String img) {
        this.cin=cin;
        this.img=img;
    }

    public User(String cin, String firstName, String lastName, String login, String password, String img, int role) {
        this.cin = cin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.img = img;
        this.role = role;
    }

    public User(String cin, String firstName, String lastName, String login, String password, String img, int role,String bluetoothMac) {
        this.cin = cin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.img = img;
        this.role = role;
        this.bluetoothMac = bluetoothMac;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "User{" +
                "cin='" + cin + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", img='" + img + '\'' +
                ", role=" + role +
                ", bluetoothMac='" + bluetoothMac + '\'' +
                '}';
    }
}
