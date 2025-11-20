package com.sniffhub.model;

public class DogAttendance {
    // 출석 관리
    // 강아지 정보, 출석 여부, 식사 여부, 훈련 참여도, 일자
    private Dog dog;
    private boolean isPresent;
    private boolean ateMeal;
    private String trainingLevel;
    private String attendanceDate;

    public DogAttendance(Dog dog, boolean isPresent, boolean ateMeal, String trainingLevel, String attendanceDate) {
        this.dog = dog;
        this.isPresent = isPresent;
        this.ateMeal = ateMeal;
        this.trainingLevel = trainingLevel;
        this.attendanceDate = attendanceDate;
    }

    public Dog getDog() { return dog; }
    public boolean isPresent() { return isPresent; }
    public boolean getAteMeal() { return ateMeal; }
    public String getTrainingLevel() { return trainingLevel; }
    public String getAttendanceDate() { return attendanceDate; }
}