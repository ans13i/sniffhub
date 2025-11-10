package com.sniffhub.view;

import com.sniffhub.model.Dog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/* -----------------------------
 * 검색 창(SearchFrame)
 *  - 생성 시: 전체 강아지 목록 + 검색어(ownerQuery)를 받아서
 *  - 보호자 이름에 검색어를 포함하는 강아지들만 테이블로 표시
 *  - UI: BorderLayout + JTable(스크롤)
 * ----------------------------- */

public class SearchFrame extends JFrame {
    public SearchFrame(ArrayList<Dog> filteredDogs, String ownerQuery) {

        // 윈도우(프레임) 기본 설정
        setTitle("검색 결과 - " + ownerQuery);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // TABLE에 표시할 데이터
        String[] columns = {"이름", "나이", "반", "크기", "품종"};
        Object[][] data = new Object[filteredDogs.size()][columns.length];

        for (int i = 0; i < filteredDogs.size(); i++) {
            Dog d = filteredDogs.get(i);
            data[i][0] = d.getName();
            data[i][1] = d.getAge();
            data[i][2] = d.getKlass();
            data[i][3] = d.getSize();
            data[i][4] = d.getBreed();
        }

        // JTable 모델 생성 및 스크롤 패널에 추가
        JTable table = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
    }
}