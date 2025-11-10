package com.sniffhub.controller;

import com.sniffhub.model.DogManagementModel;
import com.sniffhub.view.Main;

public class LoginController {
    private final Main app;
    private final DogManagementModel model;

    public LoginController(Main app, DogManagementModel model) {
        this.app = app;
        this.model = model;
    }

    // 로그인 ID를 확인하고 결과를 반환
    public boolean managerLogin(String inputId) {
        return "0000".equals(inputId.trim());
    }

    // 로그인 성공 시 화면 전환
    public void proceedToMain() {
        app.switchToMainMenu();
    }
}