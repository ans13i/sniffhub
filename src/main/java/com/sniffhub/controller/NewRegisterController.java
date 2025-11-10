package com.sniffhub.controller;

import com.sniffhub.view.Main;

import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;

public class NewRegisterController {
    private Main app;
    private DogManagementModel model;

    public NewRegisterController(Main app, DogManagementModel model) {
        this.app = app;
        this.model = model;
    }

    // 등록 버튼 클릭 시
    public void newRegisterDog(String oName, String oAddr, String oPhone,
                               String dName, String dAge, String dSize, String dBreed, JDialog dialog) {

        // 기본 유효성 검사
        try {
            if (oName.isEmpty() || dName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "필수 정보를 입력하세요.");
                return;
            }

            // 나이가 숫자인지 확인
            int age = Integer.parseInt(dAge.trim());

            // 새로 등록된 강아지 model
            Dog newDog = model.registerDog(oName, oAddr, oPhone, dName, age, dSize, dBreed);

            // 새로 등록된 강아지 model을 반 배정하고 view로 보여줌
            JOptionPane.showMessageDialog(dialog, newDog.getName() + "은(는) " + newDog.getKlass() + "에 배정되었습니다.");
            dialog.dispose();
        }
        // 예외 처리
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "나이는 숫자로 입력하세요.");
        }
    }
}