package se.plweb;

import java.sql.*;
import java.util.Properties;

public class DBPopulate {

    private Connection connection;
    private PreparedStatement preparedStatement;

    DBPopulate() {
        String url = "jdbc:postgresql://localhost:5432/peterlindblom";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "");
        props.setProperty("ssl", "false");
        try {
            connection = DriverManager.getConnection(url, props);
            preparedStatement = connection.prepareStatement("INSERT INTO test1 (name, age) VALUES(?, ?);");
        } catch (SQLException e) {
            //
        }
    }

    public boolean addDataData(String name, int age) {
        if (connection != null && preparedStatement != null) {
            try {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.addBatch();
                return true;
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }
        return false;
    }

    public void executeBatch() {
        if (connection != null && preparedStatement != null) {
            try {
                preparedStatement.executeBatch();
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }

    }
}
