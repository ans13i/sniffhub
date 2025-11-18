package com.sniffhub.view;

import com.sniffhub.controller.SearchController;
import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;

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

    private SearchController controller;

    public SearchFrame(ArrayList<Dog> filteredDogs, String ownerQuery, DogManagementModel model) {

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
        this.controller = new SearchController(model, this.filteredDogs, this.tableModel, this);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
                        controller.deleteDog(row);
                    }
                }

                // 2) "삭제" 칼럼이 아닌 칸을 더블클릭 → 그 행 전체 수정
                if (e.getClickCount() == 2 && col != lastCol) {
                    controller.editDog(row);
                }
            }
        });
    }
}
