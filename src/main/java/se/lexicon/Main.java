package se.lexicon;


import se.lexicon.dao.StudentDao;
import se.lexicon.dao.StudentDaoImpl;
import se.lexicon.db.DatabaseConnection;

import javax.sql.DataSource;
import java.sql.*;
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

            studentDao.findAll().forEach(IO::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // We want to add parameters to the query
    private static void preparedStatement() {
        // Establish Database Connection + Closing happens here too
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Create PreparedStatement
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, class_group, create_date FROM student WHERE class_group = ?;")
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
