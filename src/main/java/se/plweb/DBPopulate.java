package se.plweb;

import java.sql.*;
import java.util.Properties;

public class DBPopulate {

    private Connection connection;

    DBPopulate() {
        String url = "jdbc:postgresql://localhost:5432/peterlindblom";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "");
        props.setProperty("ssl", "false");
        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            //
        }

    }

    public boolean addDataData(String name, int age) {
        if (connection != null) {
            try {
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO test1 (name, age) VALUES(?, ?);");
                 preparedStatement.setString(1, name);
                 preparedStatement.setInt(2, age);
                 preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }
        return false;
    }
}
