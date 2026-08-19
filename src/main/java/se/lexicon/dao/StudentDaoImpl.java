package se.lexicon.dao;

import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
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
