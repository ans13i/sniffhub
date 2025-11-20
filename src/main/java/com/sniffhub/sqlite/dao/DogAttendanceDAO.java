package com.sniffhub.sqlite.dao;

import com.sniffhub.model.DogAttendance;
import com.sniffhub.sqlite.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 강아지 출석(Dog Attendance) DAO
 * - 등록, 전체 조회, 강아지 기반 조회 기능 제공
 */
public class DogAttendanceDAO {

    /**
     * 특정 강아지 & 특정 날짜 출석 기록 존재 여부 확인
     * @param dogAttendance 강아지 출석 객체
     * @return 데이터 있으면 true, 없으면 false
     */
    public boolean existsAttendance(DogAttendance dogAttendance) {
        String sql = """
                SELECT 1
                  FROM dog_attendance
                 WHERE del_yn = 'N'
                   AND dog_id = ?
                   AND attendance_date = ?
                LIMIT 1
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dogAttendance.getDog().getId());
            ps.setString(2, dogAttendance.getAttendanceDate());

            ResultSet rs = ps.executeQuery();

            return rs.next();   // 하나라도 있으면 true

        } catch (Exception e) {
            System.err.println("출석 존재 여부 조회 실패: " + e.getMessage());
        }

        return false;
    }

    /**
     * 강아지 출석 등록
     * @param dogAttendance 강아지 출석 객체
     */
    public void insertAttendance(DogAttendance dogAttendance) {
        String sql = """
                INSERT INTO dog_attendance 
                (dog_id, attendance_date, is_present, ate_meal, training_level)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dogAttendance.getDog().getId());
            ps.setString(2, dogAttendance.getAttendanceDate());
            ps.setInt(3, dogAttendance.isPresent() ? 1 : 0);
            ps.setInt(4, dogAttendance.getAteMeal() ? 1 : 0);
            ps.setString(5, dogAttendance.getTrainingLevel());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("강아지 출석 등록 실패: " + e.getMessage());
        }
    }

    /**
     * 강아지 출석 수정
     * @param dogAttendance 강아지 출석 객체
     */
    public void updateAttendance(DogAttendance dogAttendance) {
        String sql = """
                UPDATE dog_attendance
                   SET is_present = ?, 
                       ate_meal = ?, 
                       training_level = ?, 
                       updated_at = datetime('now','localtime')
                 WHERE dog_id = ?
                   AND attendance_date = ?
                   AND del_yn = 'N'
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dogAttendance.isPresent() ? 1 : 0);
            ps.setInt(2, dogAttendance.getAteMeal() ? 1 : 0);
            ps.setString(3, dogAttendance.getTrainingLevel());
            ps.setInt(4, dogAttendance.getDog().getId());
            ps.setString(5, dogAttendance.getAttendanceDate());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("강아지 출석 수정 실패: " + e.getMessage());
        }
    }

}
