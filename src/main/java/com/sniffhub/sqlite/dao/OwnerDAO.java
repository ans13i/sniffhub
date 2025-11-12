package com.sniffhub.sqlite.dao;

import com.sniffhub.model.Owner;
import com.sniffhub.sqlite.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 보호자(Owner) DAO
 * - 등록, 전체 조회, 조건 검색 기능 제공
 */
public class OwnerDAO {

    /**
     * 보호자 등록
     * @param owner 등록할 보호자 정보 객체
     * @return 생성된 보호자 PK
     */
    public int insertOwner(Owner owner) {
        String sql = "INSERT INTO owner (name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            ps.setString(3, owner.getPhone());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : 0;

        } catch (Exception e) {
            System.err.println("보호자 등록 실패: " + e.getMessage());
            return -1;
        }
    }

    /**
     * 보호자 이름으로 보호자 조회
     * @param name 보호자 이름
     * @return 보호자 객체
     */
    public Owner findOwnerByName(String name) {
        String sql = "SELECT * FROM owner WHERE name = ? AND del_yn = 'N'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Owner(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            System.err.println("보호자 조회 실패: " + e.getMessage());
        }
        return null;
    }

    /**
     * 전체 보호자 조회
     * @return 보호자 객체 리스트
     */
    public ArrayList<Owner> findAllOwner() {
        ArrayList<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owner WHERE del_yn = 'N'";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                owners.add(new Owner(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (Exception e) {
            System.err.println("보호자 전체 조회 실패: " + e.getMessage());
        }
        return owners;
    }


}
