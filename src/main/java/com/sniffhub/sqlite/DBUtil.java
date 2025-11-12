package com.sniffhub.sqlite;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * SQLite 데이터베이스 관리 유틸리티 클래스
 */
public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:sniffhub.db";

    // DB 연결
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    // DDL(SQL) 파일 실행 → 테이블 생성
    public static void initDatabase() {
        try (Connection conn = getConnection();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(DBUtil.class.getResourceAsStream("/sniffhub_ddl.sql")))
             );
             Statement stmt = conn.createStatement()
        ) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            stmt.executeUpdate(sql.toString());
        } catch (Exception e) {
            System.err.println("DB 초기화 실패: " + e.getMessage());
        }
    }
}
