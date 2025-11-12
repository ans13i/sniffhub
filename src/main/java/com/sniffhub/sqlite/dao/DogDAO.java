package com.sniffhub.sqlite.dao;

import com.sniffhub.model.Dog;
import com.sniffhub.model.Owner;
import com.sniffhub.sqlite.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * 강아지(Dog) DAO
 * - 등록, 전체 조회, 보호자 이름 기반 조회 기능 제공
 */
public class DogDAO {

    /**
     * 강아지 등록
     * @param dog 등록할 강아지 정보 객체
     * @param ownerId 보호자 PK
     */
    public void insertDog(Dog dog, int ownerId) {
        String sql = "INSERT INTO dog (name, age, size, breed, klass, owner_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dog.getName());
            ps.setInt(2, dog.getAge());
            ps.setString(3, dog.getSize());
            ps.setString(4, dog.getBreed());
            ps.setString(5, dog.getKlass());
            ps.setInt(6, ownerId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("강아지 등록 실패: " + e.getMessage());
        }
    }

    /**
     * 전체 강아지 조회
     * @return 강아지 객체 리스트
     */
    public ArrayList<Dog> findAllDogs() {
        ArrayList<Dog> dogs = new ArrayList<>();
        String sql = """
                SELECT d.*, o.name AS owner_name, o.address AS owner_address, o.phone AS owner_phone
                  FROM dog d
            INNER JOIN owner o ON d.owner_id = o.id AND o.del_yn = 'N'
                WHERE d.del_yn = 'N'
                """;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Owner owner = new Owner(
                        rs.getString("owner_name"),
                        rs.getString("owner_address"),
                        rs.getString("owner_phone")
                );
                dogs.add(new Dog(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("size"),
                        rs.getString("breed"),
                        rs.getString("klass"),
                        owner
                ));
            }
        } catch (Exception e) {
            System.err.println("강아지 전체 조회 실패: " + e.getMessage());
        }
        return dogs;
    }

    /**
     * 보호자 이름으로 강아지 조회
     * @param ownerName 보호자 이름
     * @return 강아지 객체 리스트
     */
    public ArrayList<Dog> findDogByOwnerName(String ownerName) {
        ArrayList<Dog> dogs = new ArrayList<>();
        String sql = """
                SELECT d.*, o.name AS owner_name, o.address AS owner_address, o.phone AS owner_phone
                  FROM dog d
            INNER JOIN owner o ON d.owner_id = o.id AND o.del_yn = 'N'
                 WHERE d.del_yn = 'N' AND o.name LIKE ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + ownerName + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Owner owner = new Owner(
                        rs.getString("owner_name"),
                        rs.getString("owner_address"),
                        rs.getString("owner_phone")
                );
                dogs.add(new Dog(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("size"),
                        rs.getString("breed"),
                        rs.getString("klass"),
                        owner
                ));
            }
        } catch (Exception e) {
            System.err.println("보호자 이름으로 강아지 조회 실패: " + e.getMessage());
        }
        return dogs;
    }

}
