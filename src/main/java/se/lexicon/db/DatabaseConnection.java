package se.lexicon.db;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/school_management";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static Connection connection;

    // This is just a beginner way
/*    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }*/

    // Using DataSource is a much better way
    private static DataSource mySQLDataSource;

    public static DataSource getMySQLDataSource() {
        if (mySQLDataSource == null) {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);
            mySQLDataSource = ds;
        }
        return mySQLDataSource;
    }
}
