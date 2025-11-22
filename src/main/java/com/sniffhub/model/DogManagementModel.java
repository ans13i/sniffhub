package com.sniffhub.model;

import com.sniffhub.sqlite.dao.DogAttendanceDAO;
import com.sniffhub.sqlite.dao.DogDAO;
import com.sniffhub.sqlite.dao.OwnerDAO;

import java.util.ArrayList;

// 강아지 유치원 관리 DogManagementModel Class
public class DogManagementModel {

    // 보호자 DAO
    private final OwnerDAO ownerDAO = new OwnerDAO();
    // 강아지 DAO
    private final DogDAO dogDAO = new DogDAO();
    // 강아지 출석 DAO
    private final DogAttendanceDAO dogAttendanceDAO = new DogAttendanceDAO();

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
        // 보호자 이름/주소/연락처로 보호자 고유 아이디 조회 (기등록 여부 확인)
        int ownerId = ownerDAO.getOwnerId(oName, oAddr, oPhone);
        // 보호자 미존재 시 신규 생성 (ownerId = 0)
        if (ownerId == 0) {
            Owner ownerParam = new Owner(oName, oAddr, oPhone);
            ownerId = ownerDAO.insertOwner(ownerParam);
        }

        // 2. 나이 기준으로 반 배정
        String klass = assignClassByAge(dAge);

        // 3. Dog 객체 생성 및 저장소에 추가
        Dog newDog = new Dog(dName, dAge, dSize, dBreed, klass, null);
        // 강아지 신규 생성
        dogDAO.insertDog(newDog, ownerId);

        return newDog;
    }

    // 보호자 이름으로 검색 시 강아지 리스트
    public ArrayList<Dog> filterDogsByOwner(String ownerQuery) {
        // 검색조건 강아지 리스트 DB 조회
        return dogDAO.findDogByOwnerName(ownerQuery);
    }

    // 해당 반의 강아지 리스트
    public ArrayList<Dog> getDogsByKlass(String klass) {
        // 검색조건 강아지 리스트 DB 조회
        return dogDAO.findDogsByKlass(klass);
    }

    // 해당 반의 출석 여부, 식사 여부, 훈련 참여도 수정 후 저장
    public void saveAttendanceData(ArrayList<DogAttendance> attendanceRecordToSave) {
        // 리스트 반복 DB 저장
        for (DogAttendance dogAttendance : attendanceRecordToSave) {
            // 출석 존재여부 확인
            // 출석 미존재 시 신규 등록, 존재 시 수정
            if (dogAttendanceDAO.existsAttendance(dogAttendance)) {
                dogAttendanceDAO.updateAttendance(dogAttendance);
            } else {
                dogAttendanceDAO.insertAttendance(dogAttendance);
            }
        }
    }

    // 강아지 삭제
    public void deleteDog(int dogId) {
        // 강아지 DB 논리삭제
        dogDAO.deleteDogById(dogId);
    }

    // 강아지 수정
    public void updateDog(Dog dog) {
        // 강아지 DB 수정
        dogDAO.updateDog(dog);
    }
}