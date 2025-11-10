package com.sniffhub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/* -----------------------------
 * 데이터 모델: 보호자(Owner)
 *  - 강아지의 보호자 정보를 하나로 묶어 관리
 *  - 문자열로 흩어두는 대신 객체로 들고다니면 확장/재사용 쉬움
 * ----------------------------- */
class Owner {
    String name, address, phone; // 이름, 주소, 전화번호
    Owner(String name, String address, String phone) {
        this.name = name; this.address = address; this.phone = phone;
    }
}

/* -----------------------------
 * 데이터 모델: 강아지(Dog)
 *  - 강아지 기본 정보 + 어떤 보호자에게 속하는지(Owner)
 *  - owner 필드 = 연관관계(Association)
 *    → Dog가 자신의 보호자를 직접 참조하므로, d.owner.name 등으로 접근 가능
 * ----------------------------- */
class Dog {
    String name, size, breed, klass; // 이름, 소형/대형, 견종, 배정 반(klass로 명명)
    int age;
    Owner owner;                     // 이 강아지의 보호자

    Dog(String name, int age, String size, String breed, Owner owner, String klass) {
        this.name = name; this.age = age; this.size = size; this.breed = breed;
        this.owner = owner; this.klass = klass;
    }
}

/* -----------------------------
 * 검색 창(SearchFrame)
 *  - 생성 시: 전체 강아지 목록 + 검색어(ownerQuery)를 받아서
 *  - 보호자 이름에 검색어를 포함하는 강아지들만 테이블로 표시
 *  - UI: BorderLayout + JTable(스크롤)
 * ----------------------------- */
class SearchFrame extends JFrame {
    SearchFrame(ArrayList<Dog> allDogs, String ownerQuery) {

        // 윈도우(프레임) 기본 설정
        setTitle("검색 결과 - " + ownerQuery);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10)); // 상하좌우로 구역 나눔

        // 1) 필터링: 보호자 이름에 검색어(ownerQuery)가 포함된 강아지만 추출
        ArrayList<Dog> rows = new ArrayList<>();
        for (Dog d : allDogs) {
            if (d.owner != null && d.owner.name.contains(ownerQuery)) {
                rows.add(d);
            }
        }

        // 2) 테이블에 넣을 데이터 준비
        String[] columns = {"이름", "나이", "반", "크기", "품종"};
        Object[][] data = new Object[rows.size()][columns.length];
        for (int i = 0; i < rows.size(); i++) {
            Dog d = rows.get(i);
            data[i][0] = d.name;
            data[i][1] = d.age;
            data[i][2] = d.klass;
            data[i][3] = d.size;
            data[i][4] = d.breed;
        }

        // 3) JTable 구성 + 스크롤 붙이기(행이 많아져도 UI 깨지지 않게)
        JTable table = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
    }
}

/* -----------------------------
 * 메인 앱(MainApp)
 *  - 로그인 화면 → 메인 메뉴(신규등록/검색/출석관리 버튼)
 *  - '출석 관리' 버튼은 요구사항대로 "표시만 하고 동작 없음"
 *  - 신규 등록은 다이얼로그 띄워서 강아지/보호자 입력 → 메모리에 저장
 * ----------------------------- */
public class MainApp extends JFrame {
    // 메모리 내 저장소(학습/연습용): DB 붙이기 전 단계에서 ArrayList로 관리
    private ArrayList<Owner> owners = new ArrayList<>();
    private ArrayList<Dog> dogs = new ArrayList<>();

    private JPanel loginPanel; // 로그인 화면 패널
    private JPanel mainPanel;  // 메인 메뉴 화면 패널

    public MainApp() {
        // 프레임(최상위 창) 기본 설정
        setTitle("SniffHub 기본 버전");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 시작 화면은 로그인 패널로
        buildLoginPanel();
        setContentPane(loginPanel); // 현재 화면(컨텐트팬) 교체
    }

    /* -----------------------------
     * 로그인 화면
     *  - 레이아웃: BorderLayout
     *  - 중앙에 ID 입력 + 확인 버튼
     *  - ID가 "0000"이면 메인 화면으로 전환
     * ----------------------------- */
    private void buildLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("sniffhub", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginPanel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout());
        JLabel lbl = new JLabel("ID:");
        JTextField tf = new JTextField(15);
        JButton ok = new JButton("확인");
        center.add(lbl); center.add(tf); center.add(ok);
        loginPanel.add(center, BorderLayout.CENTER);

        JLabel msg = new JLabel("관리자 암호를 입력하세요.", SwingConstants.CENTER);
        loginPanel.add(msg, BorderLayout.SOUTH);






        // 확인 버튼 클릭 시: 간단한 인증 → 성공하면 메인 화면 구성 후 전환
        ok.addActionListener(e -> {
            String input = tf.getText().trim();
            if (input.equals("0000")) {
                buildMainPanel();
                setContentPane(mainPanel);
                revalidate(); // 레이아웃 새로고침(화면 전환 반영)
            } else {
                JOptionPane.showMessageDialog(this, "ID가 올바르지 않습니다.");
            }
        });
    }

    /* -----------------------------
     * 메인 메뉴 화면
     *  - 레이아웃: GridLayout(세로 3단 버튼)
     *  - 신규 등록: 입력 다이얼로그 오픈
     *  - 검색: 보호자 이름 입력받아 SearchFrame 띄움
     *  - 출석 관리: 버튼만 표시(요구사항에 따라 동작 없음)
     * ----------------------------- */
    private void buildMainPanel() {
        mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton btnNew = new JButton("신규 등록");
        JButton btnSearch = new JButton("검색");
        JButton btnAttend = new JButton("출석 관리"); // 표시만, 이벤트 연결 안 함

        mainPanel.add(btnNew);
        mainPanel.add(btnSearch);
        mainPanel.add(btnAttend);


        // 신규 등록: 다이얼로그 열기
        btnNew.addActionListener(e -> showRegisterDialog());

        // 검색: 보호자 이름을 입력받아 결과 창 띄움
        btnSearch.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(this, "보호자 이름:");
            if (q != null && !q.trim().isEmpty()) {
                new SearchFrame(dogs, q.trim()).setVisible(true);
            }
        });


    }

    /* -----------------------------
     * 신규 등록 다이얼로그
     *  - 보호자/강아지 정보를 입력 → 유효성 검사 → 메모리에 저장
     *  - 보호자는 이름으로 중복 체크 후 없으면 새로 생성
     * ----------------------------- */
    private void showRegisterDialog() {
        JDialog dlg = new JDialog(this, "신규 등록", true);
        dlg.setSize(500, 300);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(10,10));

        // 왼쪽: 보호자 입력
        JPanel left = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField tfOName = new JTextField();
        JTextField tfOAddr = new JTextField();
        JTextField tfOPhone = new JTextField();
        left.add(new JLabel("보호자 이름")); left.add(tfOName);
        left.add(new JLabel("보호자 주소")); left.add(tfOAddr);
        left.add(new JLabel("보호자 연락처")); left.add(tfOPhone);

        // 오른쪽: 강아지 입력
        JPanel right = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField tfDName = new JTextField();
        JTextField tfDAge = new JTextField();
        JComboBox<String> cbSize = new JComboBox<>(new String[]{"소형견", "대형견"});
        JTextField tfDBreed = new JTextField();
        right.add(new JLabel("강아지 이름")); right.add(tfDName);
        right.add(new JLabel("강아지 나이")); right.add(tfDAge);
        right.add(new JLabel("크기")); right.add(cbSize);
        right.add(new JLabel("품종")); right.add(tfDBreed);

        // 가운데: 좌/우 패널 합치기
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(left); center.add(right);
        dlg.add(center, BorderLayout.CENTER);

        // 하단: 등록 버튼
        JButton btnSubmit = new JButton("등록");
        JPanel bottom = new JPanel();
        bottom.add(btnSubmit);
        dlg.add(bottom, BorderLayout.SOUTH);

        // 등록 버튼 동작
        btnSubmit.addActionListener(e -> {
            try {
                String oName = tfOName.getText().trim();
                String oAddr = tfOAddr.getText().trim();
                String oPhone = tfOPhone.getText().trim();
                String dName = tfDName.getText().trim();
                int age = Integer.parseInt(tfDAge.getText().trim());
                String dSize = (String) cbSize.getSelectedItem();
                String dBreed = tfDBreed.getText().trim();

                // 필수 값 검증(보호자 이름, 강아지 이름, 나이)
                if (oName.isEmpty() || dName.isEmpty()) {
                    JOptionPane.showMessageDialog(dlg, "필수 정보를 입력하세요.");
                    return;
                }



                // 보호자 중복 체크: 이름으로 동일 인물 있으면 재사용
                Owner o = findOwnerByName(oName);
                if (o == null) {
                    o = new Owner(oName, oAddr, oPhone);
                    owners.add(o);
                }

                // 나이에 따른 반 배정
                String klass = assignClassByAge(age);


                // 강아지 등록(보호자 참조 포함)
                dogs.add(new Dog(dName, age, dSize, dBreed, o, klass));


                JOptionPane.showMessageDialog(dlg, dName + "은 " + klass + "에 배정되었습니다.");
                dlg.dispose();

            } catch (NumberFormatException ex) {
                // 나이가 정수가 아닐 때
                JOptionPane.showMessageDialog(dlg, "나이는 숫자로 입력하세요.");
            }
        });

        dlg.setVisible(true);
    }

    /* -----------------------------
     * 이름으로 보호자 찾기
     * ----------------------------- */
    private Owner findOwnerByName(String name) {
        for (Owner o : owners) if (o.name.equals(name)) return o;
        return null;
    }

    /* -----------------------------
     * 유틸: 나이에 따른 반 배정 규칙
     *  - 0~1살: 사회반
     *  - 2~4살: 놀이반
     *  - 5살 이상: 교육반
     * ----------------------------- */
    private String assignClassByAge(int age) {
        if (age <= 1) return "사회반";
        else if (age <= 4) return "놀이반";
        else return "교육반";
    }

    /* -----------------------------
     * 프로그램 시작점
     *  - EDT(Event Dispatch Thread)에서 GUI 실행
     * ----------------------------- */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
