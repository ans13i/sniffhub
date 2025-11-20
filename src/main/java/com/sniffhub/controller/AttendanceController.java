package com.sniffhub.controller;

import com.sniffhub.model.DogManagementModel;
import com.sniffhub.model.Dog;
import com.sniffhub.model.DogAttendance;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceController {
    private DogManagementModel model;

    public AttendanceController(DogManagementModel model) {
        this.model = model;
    }

    // 저장 버튼 클릭 시 모든 데이터를 DTO 리스트로 반환하여 model에 전달
    public void saveAttendanceData(ArrayList<Dog> klassDogs, DefaultTableModel tableModel) {
        int rowCount = tableModel.getRowCount();

        // 저장할 출석 기록들
        ArrayList<DogAttendance> attendanceRecordToSave = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Dog dog = klassDogs.get(i); // 강아지

            // JTable에서 수정한 값(출석 여부, 식사 여부, 훈련 참여도)
            Boolean isPresent = (Boolean) tableModel.getValueAt(i, 5);
            Boolean ateMeal   = (Boolean) tableModel.getValueAt(i, 6);
            String training   = (String) tableModel.getValueAt(i, 7);

            // DTO 객체로 만든 후 리스트에 추가
            attendanceRecordToSave.add(new DogAttendance(dog, isPresent, ateMeal, training, LocalDate.now().toString()));
        }

        model.saveAttendanceData(attendanceRecordToSave);
    }
}