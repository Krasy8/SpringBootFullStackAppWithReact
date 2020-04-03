package com.krasy8.fullStackApp.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender " +
                "FROM student";

        return jdbcTemplate.query(sql, mapStudentRowMapper());
    }

    int insertStudent(UUID studentId, Student student) {
        String sql = "" +
                "INSERT INTO student (student_id, first_name, last_name, email, gender) " +
                "VALUES(?, ?, ?, ?, ?::gender)";
        return jdbcTemplate.update(
                sql,
                studentId,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase()
        );
    }

    private RowMapper<Student> mapStudentRowMapper() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            String genderStr = resultSet.getString("gender").toUpperCase();
            Student.Gender gender = Student.Gender.valueOf(genderStr);
            return new Student(
                    studentId,
                    firstName,
                    lastName,
                    email,
                    gender
            );
        };
    }

    boolean isEmailTaken(String email) {
        String sql = "" +
                "SELECT EXISTS (SELECT 1 FROM student WHERE email = ?)";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {email},
                (resultSet, i) -> resultSet.getBoolean(1)
        );
    }

    public List<StudentCourse> SelectAllCoursesByStudentId(UUID studentId) {
        String sql = "" +
                "SELECT * " +
                "FROM student " +
                "JOIN student_course USING (student_id) " +
                "JOIN course USING (course_id) WHERE student_id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[] {studentId},
                mapStudentCoursesRowMapper());
    }

    private RowMapper<StudentCourse> mapStudentCoursesRowMapper() {
        return (resultSet, i) -> {
            return new StudentCourse(
                    UUID.fromString(resultSet.getString("student_id")),
                    UUID.fromString(resultSet.getString("course_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("department"),
                    resultSet.getString("teacher_name"),
                    resultSet.getDate("start_date").toLocalDate(),
                    resultSet.getDate("end_date").toLocalDate(),
                    Optional.ofNullable(resultSet.getString("grade"))
                            .map(Integer::parseInt)
                            .orElse(null)
            );
        };
    }

    int deleteStudentById(UUID studentId) {
        String sql = "" +
                "DELETE FROM student " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, studentId);
    }

    boolean selectExistsEmail(UUID studentId, String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM student " +
                "   WHERE student_id <> ? " +
                "    AND email = ? " +
                ")";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{studentId, email},
                (resultSet, columnIndex) -> resultSet.getBoolean(1)
        );
    }

    int updateEmail(UUID studentId, String email) {
        String sql = "" +
                "UPDATE student " +
                "SET email = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, email, studentId);
    }

    int updateFirstName(UUID studentId, String firstName) {
        String sql = "" +
                "UPDATE student " +
                "SET first_name = ? " +
                "WHERE student_id = ? ";
        return jdbcTemplate.update(sql, firstName, studentId);
    }

    int updateLastName(UUID studentId, String lastName) {
        String sql = "" +
                "UPDATE student " +
                "SET last_name = ? " +
                "WHERE student_id = ? ";
        return jdbcTemplate.update(sql, lastName, studentId);
    }
}
