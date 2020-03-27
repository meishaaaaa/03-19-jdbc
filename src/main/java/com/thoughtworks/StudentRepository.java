package com.thoughtworks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public void save(List<Student> students) {
        students.forEach(this::save);
    }

    public void save(Student student) {
        String sql =
                "insert into student(id,name,gender,admissionYear,birthday,classID) values(?,?,?,?,?,?)";

        if (!isExist(student.getId())) {
            try (Connection con = DbUtil.getConnection();
                 PreparedStatement ptmt = con.prepareStatement(sql)) {

                ptmt.setString(1, student.getId());
                ptmt.setString(2, student.getName());
                ptmt.setString(3, student.getGender());
                ptmt.setInt(4, student.getAdmissionYear());
                ptmt.setDate(5, new java.sql.Date(student.getBirthday().getTime()));
                ptmt.setString(6, student.getClassId());

                ptmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isExist(String id) {
        boolean flg = false;
        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(String.format("select count(*) from student where id='%s'", id));
             ResultSet rs = ps.executeQuery()
        ) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                flg = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flg;
    }

    public List<Student> query() {

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ptmt = con.prepareStatement("select id,name,gender,admissionYear,birthday,classID from student");
             ResultSet rs = ptmt.executeQuery()) {
            ArrayList<Student> stu = new ArrayList<>();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAdmissionYear(rs.getInt("admissionYear"));
                student.setBirthday(rs.getDate("birthday"));
                student.setClassId(rs.getString("classID"));

                stu.add(student);
            }
            return stu;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> queryByClassId(String classId) {
//        Connection con = DbUtil.getConnection();
        String sql = String.format("select id,name,gender,admissionYear,birthday,classID from student where classID='%s'",classId);
        try (Connection con = DbUtil.getConnection();
             PreparedStatement ptmt = con.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()
        ) {
            ArrayList<Student> stu = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAdmissionYear(rs.getInt("admissionYear"));
                student.setBirthday(rs.getDate("birthday"));
                student.setClassId(rs.getString("classId"));

                stu.add(student);

            }
            return stu;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(String id, Student student) {
//        Connection con = DbUtil.getConnection();

        if (isExist(student.getId())) {
            String sql =
                    "update student set id=?,name=?,gender=?,admissionYear=?,birthday=?,classID=? where id=?";
            try (Connection con = DbUtil.getConnection();
                 PreparedStatement ptmt = con.prepareStatement(sql)) {

                ptmt.setString(1, student.getId());
                ptmt.setString(2, student.getName());
                ptmt.setString(3, student.getGender());
                ptmt.setInt(4, student.getAdmissionYear());
                ptmt.setDate(5, new java.sql.Date(student.getBirthday().getTime()));
                ptmt.setString(6, student.getClassId());
                ptmt.setString(7, id);

                ptmt.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void delete(String id) {

        if (isExist(id)) {
            String sql = "delete from student where id=?";
            try (Connection con = DbUtil.getConnection();
                 PreparedStatement ptmt = con.prepareStatement(sql)) {

                ptmt.setString(1, id);

                ptmt.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

