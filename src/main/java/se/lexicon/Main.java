package se.lexicon;


import se.lexicon.dao.AttendanceDao;
import se.lexicon.dao.AttendanceDaoImpl;
import se.lexicon.dao.StudentDao;
import se.lexicon.dao.StudentDaoImpl;
import se.lexicon.db.DatabaseConnection;
import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/school_management";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    static void main() {
        /*
         * STEPS
         * Load JDBC Driver
         * Establish Database Connection
         * Create Statement / PreparedStatement
         * Execute SQL Query
         * Process ResultSet
         * Close JDBC Resources
         * */
        //statement();
        //preparedStatement();

        DataSource dataSource = DatabaseConnection.getMySQLDataSource();
        try (Connection connection = dataSource.getConnection();) {
            StudentDao studentDao = new StudentDaoImpl(connection);

            Student newStudent = new Student("Tom Hay3", "G62");
            Student savedStudent = studentDao.save(newStudent);
            IO.println("Saved student: " + savedStudent);

            studentDao.findAll().forEach(IO::println);

            AttendanceDao attendanceDao = new AttendanceDaoImpl(connection);

            Attendance newAttendance = new Attendance(newStudent, LocalDate.of(2026, 2, 8), AttendanceStatus.valueOf("PRESENT"));
            IO.println(newAttendance);
            attendanceDao.save(newAttendance);

            attendanceDao.findAll().forEach(IO::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // We want to add parameters to the query
    private static void preparedStatement() {
        // Establish Database Connection + Closing happens here too
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Create PreparedStatement
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, class_group, create_date FROM student WHERE class_group = ?")
        ) {
            IO.println("Database connection successfully established.");

            String classGroup = "G1";
            preparedStatement.setString(1, classGroup);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                IO.println("Students in class group " + classGroup);
                // Process ResultSet
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String class_group = resultSet.getString("class_group");
                    LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                    String formattedDateTime = createDate.format(DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy"));

                    IO.println("ID: " + id + "| name: " + name + "| class_group: " + class_group + "| create_date: " + formattedDateTime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database: " + e.getMessage());
        }
    }


    private static void statement() {
        // Establish Database Connection + Closing happens here too
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Create Statement
             Statement statement = connection.createStatement()
        ) {
            IO.println("Database connection successfully established.");

            String query = "SELECT id, name, class_group, create_date FROM student;";

            // Execute Query
            ResultSet resultSet = statement.executeQuery(query);
            IO.println("Any result?" + resultSet);
            // Process ResultSet
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String class_group = resultSet.getString("class_group");
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                String formattedDateTime = createDate.format(DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy"));

                IO.println("ID: " + id + "| name: " + name + "| class_group: " + class_group + "| create_date: " + formattedDateTime);
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database: " + e.getMessage());
        }
    }
}
