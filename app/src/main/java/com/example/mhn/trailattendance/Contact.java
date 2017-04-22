package com.example.mhn.trailattendance;

/**
 * Created by MHN on 12/3/2016.
 */

public class Contact {
    private int id;
    private String name;
    private int roll;
    private String email;
    private String phoneNo;
    private String batchNo;
    private String userName;
    private String password;
    private String macAddress;

    public Contact(int id, String name, int roll, String email, String phoneNo, String batchNo, String userName, String password, String macAddress, String presence) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.userName = userName;
        this.password = password;
        this.macAddress = macAddress;
        this.presence = presence;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public Contact(String name, int roll, String presence) {

        this.name = name;
        this.roll = roll;
        this.presence = presence;
    }

    private String presence;

    /*public Contact(int id, String name, int roll, String email, String phoneNo, String batchNo, String userName, String password, String macAddress, String presence) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.userName = userName;
        this.password = password;
        this.macAddress = macAddress;
        this.presence = presence;
    }*/

    public Contact(int id, String name, int roll, String email, String phoneNo, String batchNo, String userName, String password, String macAddress) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.userName = userName;
        this.password = password;
        this.macAddress = macAddress;
    }

    public Contact(String name, int roll, String email, String phoneNo, String batchNo, String macAddress) {
        this.name = name;
        this.roll = roll;

        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.macAddress = macAddress;
    }

    public Contact(String name, int roll, String email, String phoneNo, String batchNo, String userName, String password, String macAddress) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.userName = userName;
        this.password = password;
        this.macAddress = macAddress;
    }

    public Contact(int id, String name, int roll, String email, String phoneNo, String batchNo, String macAddress) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.macAddress = macAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Contact(String phoneNo, String name) {
        this.phoneNo = phoneNo;
        this.name = name;
    }

    public Contact(String name, int roll) {

        this.name = name;
        this.roll = roll;
    }

    public Contact(int id, String name, int roll, String email, String phoneNo, String batchNo) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
        this.id = id;
    }

    public Contact(String email) {

        this.email = email;
    }

    public Contact(int roll) {

        this.roll = roll;
    }

    public Contact(String name, int roll, String email, String phoneNo) {

        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Contact(String name, int roll, String email, String phoneNo, String batchNo) {

        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phoneNo = phoneNo;
        this.batchNo = batchNo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
