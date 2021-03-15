package com.p2j.smartStore.Model;

public class BluetoothSearch {

    private String bluetoothMac;
    private String firstName ;
    private String userCin;


    public BluetoothSearch() {
    }

    public BluetoothSearch(String bluetoothMac, String firstName, String userCin) {
        this.bluetoothMac = bluetoothMac;
        this.firstName = firstName;
        this.userCin = userCin;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserCin() {
        return userCin;
    }

    public void setUserCin(String userCin) {
        this.userCin = userCin;
    }

    @Override
    public String toString() {
        return "BluetoothSearch{" +
                "bluetoothMac='" + bluetoothMac + '\'' +
                ", firstName='" + firstName + '\'' +
                ", userCin='" + userCin + '\'' +
                '}';
    }
}
