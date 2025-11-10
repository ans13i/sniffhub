package com.sniffhub.view;

import com.sniffhub.controller.NewRegisterController;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;
import java.awt.*;

/* -----------------------------
 * 신규 등록 다이얼로그
 *  - 보호자/강아지 정보를 입력 → 유효성 검사 → 메모리에 저장
 *  - 보호자는 이름으로 중복 체크 후 없으면 새로 생성
 * ----------------------------- */

public class NewRegisterDialog extends JDialog {
    public NewRegisterDialog(Main app, DogManagementModel model) {
        super(app, "신규 등록", true);
        setSize(500, 300);
        setLocationRelativeTo(app);
        setLayout(new BorderLayout(10, 10));

        // View(app)와 Model 객체를 Controller의 생성자로 정달
        NewRegisterController controller = new NewRegisterController(app, model);

        // 왼쪽(보호자)
        JPanel left = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField tfOName = new JTextField();
        JTextField tfOAddr = new JTextField();
        JTextField tfOPhone = new JTextField();
        left.add(new JLabel("보호자 이름")); left.add(tfOName);
        left.add(new JLabel("보호자 주소")); left.add(tfOAddr);
        left.add(new JLabel("보호자 연락처")); left.add(tfOPhone);

        // 오른쪽(강아지)
        JPanel right = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField tfDName = new JTextField();
        JTextField tfDAge = new JTextField();
        JComboBox<String> cbSize = new JComboBox<>(new String[]{"소형견", "대형견"});
        JTextField tfDBreed = new JTextField();
        right.add(new JLabel("강아지 이름")); right.add(tfDName);
        right.add(new JLabel("강아지 나이")); right.add(tfDAge);
        right.add(new JLabel("크기")); right.add(cbSize);
        right.add(new JLabel("품종")); right.add(tfDBreed);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        JButton btnSubmit = new JButton("등록");
        JPanel bottom = new JPanel();
        bottom.add(btnSubmit);
        add(bottom, BorderLayout.SOUTH);

        // 등록 버튼 클릭 시
        btnSubmit.addActionListener(e -> controller.newRegisterDog(
                tfOName.getText().trim(),
                tfOAddr.getText().trim(),
                tfOPhone.getText().trim(),
                tfDName.getText().trim(),
                tfDAge.getText().trim(),
                (String) cbSize.getSelectedItem(),
                tfDBreed.getText().trim(),
                // 다이얼로그 자신을 넘겨서 Controller가 창을 닫을 수 있게 함
                this
        ));
    }
}