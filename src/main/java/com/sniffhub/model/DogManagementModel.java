package com.sniffhub.model;

import com.sniffhub.sqlite.dao.DogDAO;
import com.sniffhub.sqlite.dao.OwnerDAO;

import java.util.ArrayList;

// 강아지 유치원 관리 DogManagementModel Class
public class DogManagementModel {

    // 보호자 DAO
    private final OwnerDAO ownerDAO = new OwnerDAO();
    // 강아지 DAO
    private final DogDAO dogDAO = new DogDAO();

    // 나이를 기준으로 한 강아지 반 배정
    public String assignClassByAge(int age) {
        if (age <= 1) return "사회반";
        else if (age <= 4) return "놀이반";
        else return "교육반";
    }

    // 신규 강아지 등록
    public Dog registerDog(String oName, String oAddr, String oPhone,
                           String dName, int dAge, String dSize, String dBreed) {

        // 1. 보호자 확인 또는 신규 생성
        // 보호자 이름으로 보호자 조회
        Owner owner = ownerDAO.findOwnerByName(oName);

        // 보호자 미존재 시 신규 생성
        int ownerId = 0;
        if (owner == null) {
            owner = new Owner(oName, oAddr, oPhone);
            // 보호자 신규 생성
            ownerId = ownerDAO.insertOwner(owner);
        }

        // 2. 나이 기준으로 반 배정
        String klass = assignClassByAge(dAge);

        // 3. Dog 객체 생성 및 저장소에 추가
        Dog newDog = new Dog(dName, dAge, dSize, dBreed, klass, owner);
        // 강아지 신규 생성
        dogDAO.insertDog(newDog, ownerId);

        return newDog;
    }

    // 보호자 이름으로 검색 시 강아지 리스트
    public ArrayList<Dog> filterDogsByOwner(String ownerQuery) {
        return dogDAO.findDogByOwnerName(ownerQuery);
    }

    // 모든 강아지 리스트
    public ArrayList<Dog> getAllDogs() {
        return dogDAO.findAllDogs();
    }

    // 모든 보호자 리스트
    public ArrayList<Owner> getAllOwners() {
        return ownerDAO.findAllOwner();
    }

    // 해당 반의 강아지 리스트
    public ArrayList<Dog> getDogsByKlass(String klass) {
        // @TODO: 예) 사회화반 강아지만 찾도록 요청 WHERE
        // @TODO: dogDAO에 findDogsByKlass(String klass) 만들어야 함

        // 임시로 만듦
        ArrayList<Dog> allDogs = this.getAllDogs();
        ArrayList<Dog> klassDogs = new ArrayList<>();

        for(Dog d : allDogs){
            if(d.getKlass().equals(klass)){
                klassDogs.add(d);
            }
        }
        return klassDogs;
    }

    // 해당 반의 출석 여부, 식사 여부, 훈련 참여도 수정 후 저장
    public void saveAttendanceData(ArrayList<DogAttendance> attendanceRecordToSave) {
        // @TODO: dogAttendanceDAO UPDATE
    }

    // 강아지 삭제
    public void deleteDog(Dog dogDelete) {
        // @TODO  dogDAO에 deleteDogById 만들어야 할 것 같기두...합니다
    }

    // 강아지 수정
    public void updateDog(Dog dogUpdate, String newOwnerName, String newDogName, int newAge, String newKlass, String newSize, String newBread) {
        // @TODO dogDAO UPDATE
    }
}