package com.sniffhub.model;

// 강아지 Dog class
public class Dog {
    // 고유 아이디, 이름, 나이, 소형/대형, 품종, 배정 반(klass)
    private int id;
    private String name;
    private int age;
    private String size;
    private String breed;
    private String klass;

    // 보호자
    private Owner owner;

    // 강아지 출석
    private DogAttendance dogAttendance;

    // DB에 저장된 후 사용하는 생성자
    public Dog(int id, String name, int age, String size, String breed, String klass, Owner owner) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.size = size;
        this.breed = breed;
        this.klass = klass;
        this.owner = owner;
    }

    // DB에 저장되지 않았을 때 사용하는 생성자
    public Dog(String name, int age, String size, String breed, String klass, Owner owner) {
        this.name = name;
        this.age = age;
        this.size = size;
        this.breed = breed;
        this.klass = klass;
        this.owner = owner;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getSize() { return size; }
    public String getBreed() { return breed; }
    public String getKlass() { return klass; }
    public Owner getOwner() { return owner; }
    public DogAttendance getAttendance() { return dogAttendance; }
    public void setAttendance(DogAttendance dogAttendance) { this.dogAttendance = dogAttendance; }
}