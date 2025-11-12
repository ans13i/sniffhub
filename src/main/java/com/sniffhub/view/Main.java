package com.sniffhub.view;

import com.sniffhub.model.DogManagementModel;
import com.sniffhub.sqlite.DBUtil;

import javax.swing.*;

public class Main extends JFrame {
    private DogManagementModel model;

    public DogManagementModel getModel() {
        return model;
    }

    // 프레임 초기 화면
    public Main() {
        // 프레임(최상위 창) 기본 설정
        setTitle("SniffHub 기본 버전");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 프로젝트에서 사용할 Model 객체 생성
        model = new DogManagementModel();

        // 첫 화면인 LoginPanel에 Model 객체 전달
        setContentPane(new LoginPanel(this, model));
    }

    // 현재 화면에서 메인 메뉴 패널로 전환
    // 로그인 성공 시 메인 메뉴 패널로 전환
    public void switchToMainMenu() {
        setContentPane(new MainMenuBtnPanel(this, model));
        revalidate();
        repaint();
    }

    // 프로젝트 실행
    public static void main(String[] args) {
        // SQLite DB 초기화
        DBUtil.initDatabase();
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}