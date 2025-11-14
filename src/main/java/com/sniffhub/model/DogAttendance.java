package com.sniffhub.model;

public class DogAttendance {
    // 출석 관리
    // 강아지 정보, 출석 여부, 식사 여부, 훈련 참여도
    private Dog dog;
    private boolean isPresent;
    private boolean ateMeal;
    private String trainingLevel;

    public DogAttendance(Dog dog, boolean isPresent, boolean ateMeal, String trainingLevel) {
        this.dog = dog;
        this.isPresent = isPresent;
        this.ateMeal = ateMeal;
        this.trainingLevel = trainingLevel;
    }

    public Dog getDog() { return dog; }
    public boolean isPresent() { return isPresent; }
    public boolean getAteMeal() { return ateMeal; }
    public String getTrainingLevel() { return trainingLevel; }
}