package com.sniffhub.view;

import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AttendanceMenuPanel extends JPanel {

    public AttendanceMenuPanel(Main app, DogManagementModel model){
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel title = new JLabel("반을 클릭 후 출석 관리", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title);

        JButton btnSocial = new JButton("사회반");
        JButton btnPlay   = new JButton("놀이반");
        JButton btnEdu    = new JButton("교육반");

        add(btnSocial);
        add(btnPlay);
        add(btnEdu);

        // 사회반 버튼 클릭
        btnSocial.addActionListener(e -> openKlassList(app, model, "사회반"));

        // 놀이반 버튼 클릭
        btnPlay.addActionListener(e -> openKlassList(app, model, "놀이반"));

        // 교육반 버튼 클릭
        btnEdu.addActionListener(e -> openKlassList(app, model, "교육반"));
    }

    // 반 클릭 시 해당 반의 강아지 리스트 뿌려주는 메서드
    private void openKlassList(Main app, DogManagementModel model, String klass) {
        ArrayList<Dog> klassDogs = model.getDogsByKlass(klass);

        // 해당 반의 속한 강아지 출석 화면
        AttendanceKlassListDialog dialog = new AttendanceKlassListDialog(app, klassDogs, klass);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}