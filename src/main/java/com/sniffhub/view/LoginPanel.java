package com.sniffhub.view;

import com.sniffhub.controller.LoginController;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;
import java.awt.*;

/* -----------------------------
 * 관리자 로그인 화면
 *  - 레이아웃: BorderLayout
 *  - 중앙에 ID 입력 + 확인 버튼
 *  - ID가 "0000"이면 메인 화면으로 전환
 * ----------------------------- */

public class LoginPanel extends JPanel {
    public LoginPanel(Main app, DogManagementModel model) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("sniffhub", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout());
        JLabel lbl = new JLabel("ID:");
        JTextField tf = new JTextField(15);
        JButton ok = new JButton("확인");
        center.add(lbl);
        center.add(tf);
        center.add(ok);
        add(center, BorderLayout.CENTER);

        JLabel msg = new JLabel("관리자 암호를 입력하세요.", SwingConstants.CENTER);
        add(msg, BorderLayout.SOUTH);

        // View(app)와 Model(model) 객체를 Controller의 생성자로 전달
        LoginController controller = new LoginController(app, model);

        // 확인 버튼 클릭 시
        // 사용자가 입력한 ID 값을 Controller의 managerLogin 메서드로 전달
        ok.addActionListener(e -> {
            String inputId = tf.getText().trim();
            // 로그인 성공 시 Controller가 화면 전환을 수행하도록 요청
            if (controller.managerLogin(inputId)) {
                controller.proceedToMain();
            } else {
                JOptionPane.showMessageDialog(app, "ID가 올바르지 않습니다.");
            }
        });
    }
}



