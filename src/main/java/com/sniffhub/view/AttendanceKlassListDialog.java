package com.sniffhub.view;

import com.sniffhub.controller.AttendanceController;
import com.sniffhub.model.Dog;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttendanceKlassListDialog extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;

    private AttendanceController controller;
    private ArrayList<Dog> klassDogs;

    public AttendanceKlassListDialog(Main app, ArrayList<Dog> klassDogs, String klass) {
        super(app, klass + " 출석부", true);

        this.controller = new AttendanceController(app.getModel());
        this.klassDogs = klassDogs;

        setSize(800, 500);
        setLocationRelativeTo(app);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(klass + " 강아지 출석부", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"반", "강아지 이름", "나이", "크기", "품종", "출석 여부", "식사 여부", "훈련 참여도"};
        Object[][] data = new Object[klassDogs.size()][columns.length];

        for (int i = 0; i < klassDogs.size(); i++) {
            Dog d = klassDogs.get(i);
            data[i][0] = d.getKlass();
            data[i][1] = d.getName();
            data[i][2] = d.getAge();
            data[i][3] = d.getSize();
            data[i][4] = d.getBreed();
            data[i][5] = false;         // 출석 여부
            data[i][6] = false;         // 식사 여부
            data[i][7] = "normal";      // 훈련 참여도
        }

        // 커스텀 TableModel 생성 (체크박스를 위해)
        tableModel = new DefaultTableModel(data, columns) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return String.class;        // 반
                    case 1:
                        return String.class;        // 강아지 이름
                    case 2:
                        return Integer.class;       // 나이
                    case 3:
                        return String.class;        // 크기
                    case 4:
                        return String.class;        // 품종
                    case 5:
                        return Boolean.class;       // 출석 여부 (체크박스)
                    case 6:
                        return Boolean.class;       // 식사 여부 (체크박스)
                    case 7:
                        return String.class;        // 훈련 참여도
                    default:
                        return Object.class;
                }
            }

            // 출석 여부 ~ 훈련 참여도만 수정 가능하도록(index 5~7)
            @Override
            public boolean isCellEditable(int row, int columnIndex) {
                return columnIndex >= 5;
            }
        };

        table = new JTable(tableModel);

        // 훈련 참여도 드롭다운 메뉴
        String[] trainingLevels = {"low", "normal", "active", "perfect"};
        JComboBox<String> comboBox = new JComboBox<>(trainingLevels);

        // 훈련 참여도를 클릭하면 JComboBox가 나오도록 에디터 설정
        table.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBox));

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 하단 버튼 (뒤로가기, 저장)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("뒤로가기");
        JButton btnSave = new JButton("저장");

        bottomPanel.add(btnBack);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        // 뒤로가기 버튼 클릭 시 창 닫기
        btnBack.addActionListener(e -> {
            dispose();
        });

        // 저장 버튼 클릭 시 저장 메서드 호출
        btnSave.addActionListener(e -> {
            controller.saveAttendanceData(this.klassDogs, this.tableModel);

            JOptionPane.showMessageDialog(this, "저장되었습니다. (DB 연동 완료)");
            dispose();
        });
    }
}