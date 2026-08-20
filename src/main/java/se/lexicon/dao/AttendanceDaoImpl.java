package se.lexicon.dao;

import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDaoImpl implements AttendanceDao{
    private final Connection connection;

    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attendance save(Attendance attendance) {
        String sql = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ? , ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, attendance.getStudent().getId());
            preparedStatement.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            preparedStatement.setString(3, attendance.getAttendanceStatus().name());

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    attendance.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while saving attendance: " + e.getMessage());
            throw new RuntimeException("Error saving attendance", e);
        }
        return attendance;
    }

    @Override
    public List<Attendance> findAll() {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT a.id, a.attendance_date, a.status, s.id, s.name, s.class_group, s.create_date\n" +
                "FROM attendance a INNER JOIN student s ON a.student_id = s.id";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("s.id"),
                        rs.getString("s.name"),
                        rs.getString("s.class_group"),
                        rs.getTimestamp("s.create_date").toLocalDateTime()
                );
                attendanceList.add(new Attendance(
                        rs.getInt("a.id"),
                        student,
                        rs.getDate("a.attendance_date").toLocalDate(),
                        AttendanceStatus.valueOf(rs.getString("a.status").toUpperCase()) // it is important that the word is exactly like in the Enum, here: all uppercase
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving attendances: " + e.getMessage());
            throw new RuntimeException("Error retrieving attendances", e);
        }
        return attendanceList;
    }
}
