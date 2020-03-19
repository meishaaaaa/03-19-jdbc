package com.thoughtworks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentRepository {
    public final Connection con = DbUtil.getConnection();

    public void save(List<Student> students) {
        students.forEach(this::save);
    }

    public void save(Student student) {
//        Connection con = DbUtil.getConnection();

        try {
            if (isEmpty(student.getId())) {
                String sql =
                        "insert into student(id,name,gender,admissionYear,birthday,classID) values(?,?,?,?,?,?)";
                PreparedStatement ptmt = con.prepareStatement(sql);

                ptmt.setString(1, student.getId());
                ptmt.setString(2, student.getName());
                ptmt.setString(3, student.getGender());
                ptmt.setInt(4, student.getAdmissionYear());
                ptmt.setDate(5, new java.sql.Date(student.getBirthday().getTime()));
                ptmt.setString(6, student.getClassId());

                ptmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmpty(String id) {
        return String.format("select from table where id=?", id).isEmpty();
    }

    public List<Student> query() {

//        Connection con = DbUtil.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id,name,gender,admissionYear,birthday,classID from student");

            ArrayList<Student> stu = new ArrayList<>();
            Student student = null;

            while (rs.next()) {
                student = new Student();
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

        try {
            Student student = null;
            String sql = "select id,name,gender,admissionYear,birthday,classID from  student where classID=?";
            PreparedStatement preparedS = con.prepareStatement(sql);

            preparedS.setString(1, classId);
            ArrayList<Student> stu = new ArrayList<>();

            ResultSet rs = preparedS.executeQuery();
            while (rs.next()) {
                student = new Student();
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

        try {
            if (!isEmpty(student.getId())) {
                String sql = "update student set id=?,name=?,gender=?,admissionYear=?,birthday=?,classID=? where id=?";
                PreparedStatement ptmt = con.prepareStatement(sql);

                ptmt.setString(1, student.getId());
                ptmt.setString(2, student.getName());
                ptmt.setString(3, student.getGender());
                ptmt.setInt(4, student.getAdmissionYear());
                ptmt.setDate(5, new java.sql.Date(student.getBirthday().getTime()));
                ptmt.setString(6, student.getClassId());
                ptmt.setString(7, id);

                ptmt.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(String id) {
//        Connection con = DbUtil.getConnection();

        try {
            if (!isEmpty(id)) {

                String sql = "delete from student where id=?";
                PreparedStatement ptmt = con.prepareStatement(sql);

                ptmt.setString(1, id);

                ptmt.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
