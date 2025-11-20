package com.sniffhub.controller;

import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SearchController {
    private final DogManagementModel model;

    private final ArrayList<Dog> filteredDogs;
    private final DefaultTableModel tableModel;
    private final JFrame searchFrame;

    public SearchController(DogManagementModel model, ArrayList<Dog> filteredDogs, DefaultTableModel tableModel, JFrame searchFrame) {
        this.model = model;
        this.filteredDogs = filteredDogs;
        this.tableModel = tableModel;
        this.searchFrame = searchFrame;
    }

    public ArrayList<Dog> searchByOwner(String ownerName) {
        return model.filterDogsByOwner(ownerName);
    }


    // 삭제 버튼 클릭 시 강아지 삭제
    public void deleteDog(int row) {
        Dog dogDelete = filteredDogs.get(row);
        model.deleteDog(dogDelete.getId());
        filteredDogs.remove(row);
        tableModel.removeRow(row);
    }

    // 테이블 한 행을 더블 클릭 시 강아지 관련 정보 수정
    public void editDog(int row) {
        Dog dogUpdate = filteredDogs.get(row);

        // 현재 값들 가져오기
        String dogName   = String.valueOf(tableModel.getValueAt(row, 1));
        String ageStr    = String.valueOf(tableModel.getValueAt(row, 2));
        String klass     = String.valueOf(tableModel.getValueAt(row, 3));
        String size      = String.valueOf(tableModel.getValueAt(row, 4));
        String breed     = String.valueOf(tableModel.getValueAt(row, 5));

        // 입력 폼 만들기 (JOptionPane에 넣을 패널)
        JTextField tfDog   = new JTextField(dogName);
        JTextField tfAge   = new JTextField(ageStr);
        String[] KlassOptions = {"놀이반", "교육반", "사회반"};
        JComboBox<String> cbKlass = new JComboBox<>(KlassOptions);
        cbKlass.setSelectedItem(klass);


        String[] sizeOptions = {"소형견", "대형견"};
        JComboBox<String> cbSize = new JComboBox<>(sizeOptions);
        cbSize.setSelectedItem(size);

        JTextField tfBreed = new JTextField(breed);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("강아지 이름"));
        panel.add(tfDog);
        panel.add(new JLabel("나이"));
        panel.add(tfAge);
        panel.add(new JLabel("반"));
        panel.add(cbKlass);
        panel.add(new JLabel("크기"));
        panel.add(cbSize);
        panel.add(new JLabel("품종"));
        panel.add(tfBreed);

        int result = JOptionPane.showConfirmDialog(
                searchFrame,
                panel,
                "강아지 정보 수정",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // 나이는 정수만
            String ageInput = tfAge.getText().trim();
            int newAge;
            try {
                newAge = Integer.parseInt(ageInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        searchFrame,
                        "나이는 숫자로 입력하세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String newDogName = tfDog.getText().trim();
            String newKlass = (String) cbKlass.getSelectedItem();
            String newSize = (String) cbSize.getSelectedItem();
            String newBreed = tfBreed.getText().trim();

            model.updateDog(new Dog(dogUpdate.getId(), newDogName, newAge, newSize, newBreed, newKlass, null));

            tableModel.setValueAt(newDogName, row, 1);
            tableModel.setValueAt(newAge, row, 2);
            tableModel.setValueAt(newKlass, row, 3);
            tableModel.setValueAt(newSize, row, 4);
            tableModel.setValueAt(newBreed, row, 5);
        }
    }
}
