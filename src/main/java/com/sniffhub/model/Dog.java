package com.sniffhub.model;

// 강아지 Dog class
public class Dog {
    // 이름, 나이, 소형/대형, 품종, 배정 반(klass)
    private String name;
    private int age;
    private String size;
    private String breed;
    private String klass;

    // 보호자
    private Owner owner;

    public Dog(String name, int age, String size, String breed, String klass, Owner owner) {
        this.name = name;
        this.age = age;
        this.size = size;
        this.breed = breed;
        this.klass = klass;
        this.owner = owner;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getSize() { return size; }
    public String getBreed() { return breed; }
    public String getKlass() { return klass; }
    public Owner getOwner() { return owner; }
}