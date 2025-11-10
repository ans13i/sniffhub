package com.sniffhub.model;

import java.util.ArrayList;

// 강아지 유치원 관리 DogManagementModel Class
public class DogManagementModel {
    // TABLE OWNERS
    private final ArrayList<Owner> owners = new ArrayList<>();
    // TABLE DOGS
    private final ArrayList<Dog> dogs = new ArrayList<>();

    // 나이를 기준으로 한 강아지 반 배정
    public String assignClassByAge(int age) {
        if (age <= 1) return "사회반";
        else if (age <= 4) return "놀이반";
        else return "교육반";
    }

    // 보호자 이름 검색
    public Owner findOwnerByName(String name) {
        for (Owner o : owners) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        return null; // 찾지 못한 경우 null 반환
    }

    // 신규 강아지 등록
    public Dog registerDog(String oName, String oAddr, String oPhone,
                           String dName, int dAge, String dSize, String dBreed) {

        // 1. 보호자 확인 또는 신규 생성
        Owner owner = findOwnerByName(oName);

        if (owner == null) {
            owner = new Owner(oName, oAddr, oPhone);
            // 보호자 목록에 추가
            owners.add(owner);
        }

        // 2. 나이 기준으로 반 배정
        String klass = assignClassByAge(dAge);

        // 3. Dog 객체 생성 및 저장소에 추가
        Dog newDog = new Dog(dName, dAge, dSize, dBreed, klass, owner);
        // 강아지 목록에 추가
        dogs.add(newDog);

        return newDog;
    }

    // 보호자 이름으로 검색 시 강아지 리스트
    public ArrayList<Dog> filterDogsByOwner(String ownerQuery) {
        ArrayList<Dog> result = new ArrayList<>();

        for (Dog d : dogs) {
            Owner owner = d.getOwner();

            // owner가 null이 아니고, owner 이름에 검색어가 포함되어 있는지 확인
            if (owner != null && owner.getName().contains(ownerQuery)) {
                result.add(d);
            }
        }
        return result;
    }

    // 모든 강아지 리스트
    public ArrayList<Dog> getAllDogs() {
        return dogs;
    }

    // 모든 보호자 리스트
    public ArrayList<Owner> getAllOwners() {
        return owners;
    }
}