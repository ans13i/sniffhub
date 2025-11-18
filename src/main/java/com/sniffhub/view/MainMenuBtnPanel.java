package com.sniffhub.view;

import com.sniffhub.controller.SearchController;
import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/* -----------------------------
 * 메인 메뉴 화면
 *  - 레이아웃: GridLayout(세로 3단 버튼)
 *  - 신규 등록: 입력 다이얼로그 오픈
 *  - 검색: 보호자 이름 입력받아 SearchFrame 띄움
 *  - 출석 관리: 버튼만 표시(요구사항에 따라 동작 없음)
 * ----------------------------- */

public class MainMenuBtnPanel extends JPanel {
    // 메인 메뉴 패널 생성
    public MainMenuBtnPanel(Main app, DogManagementModel model) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton btnNew = new JButton("신규 등록");
        JButton btnSearch = new JButton("검색");
        JButton btnAttend = new JButton("출석 관리");

        add(btnNew);
        add(btnSearch);
        add(btnAttend);

        // 신규 등록 버튼 클릭 시
        btnNew.addActionListener(e -> new NewRegisterDialog(app, model).setVisible(true));

        // 검색 버튼 클릭 시
        btnSearch.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(app, "보호자 이름:");

            if (q != null && !q.trim().isEmpty()) {
                // model의 검색 로직을 호출하여 결과를 받음
                ArrayList<Dog> filteredDogs = model.filterDogsByOwner(q.trim());
                new SearchFrame(filteredDogs, q.trim(), model).setVisible(true);
            }
        });

        // 출석 관리 버튼 클릭 시
        btnAttend.addActionListener(e-> {
            app.setContentPane(new AttendanceMenuPanel(app, model));
            app.revalidate();
            app.repaint();
        });
    }
}