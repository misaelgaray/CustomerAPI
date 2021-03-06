package model;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static BasicDataSource dataSource;
    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/customerdb");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(15);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void CloseConnection(Connection connection,
                                       Statement statement,
                                       ResultSet resultSet) throws SQLException{
        if(connection != null && !connection.isClosed())
            connection.close();
        if(resultSet != null && !resultSet.isClosed())
            resultSet.close();
        if(statement != null && !statement.isClosed())
            statement.close();
    }
}
