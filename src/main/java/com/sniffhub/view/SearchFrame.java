package com.sniffhub.view;

import com.sniffhub.model.Dog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/* -----------------------------
 * 검색 창(SearchFrame)
 *  - 검색 결과 리스트(filteredDogs) + 검색어(ownerQuery)를 받아서
 *  - 테이블로 표시
 *  - "삭제" 칼럼 한 번 클릭 → 그 행 삭제
 *  - 그 행(삭제 칼럼 제외)을 더블클릭 → 그 행 전체 정보 수정
 * ----------------------------- */

public class SearchFrame extends JFrame {

    private ArrayList<Dog> filteredDogs;   // 검색된 강아지 목록 (삭제 시만 사용)
    private JTable table;                  // 테이블
    private DefaultTableModel tableModel;  // 테이블 모델

    public SearchFrame(ArrayList<Dog> filteredDogs, String ownerQuery) {

        this.filteredDogs = filteredDogs;

        // 윈도우(프레임) 기본 설정
        setTitle("검색 결과 - " + ownerQuery);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // TABLE에 표시할 데이터
        String[] columns = {"보호자 이름", "강아지 이름", "나이", "반", "크기", "품종", "삭제"};
        Object[][] data = new Object[filteredDogs.size()][columns.length];

        for (int i = 0; i < filteredDogs.size(); i++) {
            Dog d = filteredDogs.get(i);
            data[i][0] = d.getOwner().getName();
            data[i][1] = d.getName();
            data[i][2] = d.getAge();
            data[i][3] = d.getKlass();
            data[i][4] = d.getSize();
            data[i][5] = d.getBreed();
            data[i][6] = "삭제"; // 마지막 칸은 삭제용 텍스트
        }

        // 셀 직접 편집은 막고, 마우스 이벤트로만 수정/삭제 처리
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // JTable 생성
        table = new JTable(tableModel);

        // 스크롤 패널에 추가
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // 삭제, 수정
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());   // 클릭한 행
                int col = table.columnAtPoint(e.getPoint()); // 클릭한 열

                if (row < 0 || col < 0) return;

                int lastCol = tableModel.getColumnCount() - 1;

                // 1) "삭제" 칼럼 한 번 클릭 → 삭제
                if (e.getClickCount() == 1 && col == lastCol) {
                    int result = JOptionPane.showConfirmDialog(
                            SearchFrame.this,
                            "이 강아지를 정말 삭제할까요?",
                            "삭제 확인",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        deleteRow(row);
                    }
                }

                // 2) "삭제" 칼럼이 아닌 칸을 더블클릭 → 그 행 전체 수정
                if (e.getClickCount() == 2 && col != lastCol) {
                    editRow(row);
                }
            }
        });
    }

    //  행 삭제
    private void deleteRow(int row) {
        filteredDogs.remove(row);      // 메모리 상 검색 결과에서도 제거
        tableModel.removeRow(row);     // 화면 테이블에서 제거
    }

    // 행 전체 수정 (더블클릭 하면 됨)
    private void editRow(int row) {
        // 현재 값들 가져오기
        String ownerName = String.valueOf(tableModel.getValueAt(row, 0));
        String dogName   = String.valueOf(tableModel.getValueAt(row, 1));
        String ageStr    = String.valueOf(tableModel.getValueAt(row, 2));
        String klass     = String.valueOf(tableModel.getValueAt(row, 3));
        String size      = String.valueOf(tableModel.getValueAt(row, 4));
        String breed     = String.valueOf(tableModel.getValueAt(row, 5));

        // 입력 폼 만들기 (JOptionPane에 넣을 패널)
        JTextField tfOwner = new JTextField(ownerName);
        JTextField tfDog   = new JTextField(dogName);
        JTextField tfAge   = new JTextField(ageStr);
        JTextField tfKlass = new JTextField(klass);
        JTextField tfSize  = new JTextField(size);
        JTextField tfBreed = new JTextField(breed);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("보호자 이름"));
        panel.add(tfOwner);
        panel.add(new JLabel("강아지 이름"));
        panel.add(tfDog);
        panel.add(new JLabel("나이"));
        panel.add(tfAge);
        panel.add(new JLabel("반"));
        panel.add(tfKlass);
        panel.add(new JLabel("크기"));
        panel.add(tfSize);
        panel.add(new JLabel("품종"));
        panel.add(tfBreed);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "강아지 정보 수정",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // 나이는 정수만 허용
            String ageInput = tfAge.getText().trim();
            int newAge;
            try {
                newAge = Integer.parseInt(ageInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "나이는 숫자로 입력하세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String newOwnerName = tfOwner.getText().trim();
            String newDogName   = tfDog.getText().trim();
            String newKlass     = tfKlass.getText().trim();
            String newSize      = tfSize.getText().trim();
            String newBreed     = tfBreed.getText().trim();

            // 테이블에 값 반영
            tableModel.setValueAt(newOwnerName, row, 0);
            tableModel.setValueAt(newDogName,   row, 1);
            tableModel.setValueAt(newAge,       row, 2);
            tableModel.setValueAt(newKlass,     row, 3);
            tableModel.setValueAt(newSize,      row, 4);
            tableModel.setValueAt(newBreed,     row, 5);
        }
    }
}
