package com.sniffhub;


import javax.swing.*;

public class SniffHubApplication {
    public static void main(String[] args) {
        // 1. 창(JFrame) 만들기
        JFrame frame = new JFrame("🐾 SniffHub - 강아지 출석관리");

        // 2. 크기 지정
        frame.setSize(400, 300);

        // 3. 종료 버튼 클릭 시 프로그램 종료
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 4. 간단한 레이블 추가
        JLabel label = new JLabel("안녕하세요! SniffHub에 오신 걸 환영합니다 🐶", SwingConstants.CENTER);
        frame.add(label);

        // 5. 창을 화면 중앙에 표시
        frame.setLocationRelativeTo(null);

        // 6. 창을 보이게 하기
        frame.setVisible(true);
    }
}