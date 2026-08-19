package se.lexicon.dao;

import se.lexicon.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {
    private final Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    // This is where we save data into the database
    @Override
    public Student save(Student student) {
        // we will use that Student constructor where we only need to add the name and the classGroup
        String sql = "INSERT INTO student (name, class_group, create_date) VALUES (? , ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getClassGroup());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(student.getCreateDate()));

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                // there is only one key in this so while loop is unnecessary, we can use a simple if statement too
                //while (keys.next()) {
                if (keys.next()) {
                    student.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while saving student: " + e.getMessage());
            throw new RuntimeException("Error saving student", e);
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM student";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("class_group"),
                        rs.getTimestamp("create_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving students: " + e.getMessage());
            throw new RuntimeException("Error retrieving students", e);
        }

        return students;
    }

    @Override
    public Optional<Student> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void delete(Student student) {

    }
}
