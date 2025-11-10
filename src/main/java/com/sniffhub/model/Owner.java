package com.sniffhub.model;

// 보호자 Owner class
public class Owner {
    // 이름, 주소, 연락처
    private String name;
    private String address;
    private String phone;

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}