package service;

import model.ConnectionPool;
import model.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static final Logger log = LogManager.getLogger(CustomerService.class);

    public List<Customer> getAll() throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer order by name DESC");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()){
            Customer customer = new Customer();
            customer.convert(resultSet);
            customers.add(customer);
        }
        ConnectionPool.CloseConnection(connection,preparedStatement, resultSet);
        return customers;
    }

    public Customer get(Integer id) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Customer customer = new Customer();
            customer.convert(resultSet);
            return customer;
        }
        throw new Exception("Couldn't get customer with given id");
    }

    public Customer getByName(String name) throws Exception {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer WHERE name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            Customer customer = new Customer();
            customer.convert(resultSet);
            return customer;
        }
        return null;
    }

    public Customer edit(Customer customer) throws SQLException{
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET "+
                " name = ?, age = ?, email = ?, status = ? WHERE id = ?");
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setInt(2, customer.getAge());
        if(customer.getEmail() != null)
            preparedStatement.setString(3, customer.getEmail());
        else
            preparedStatement.setNull(3, Types.VARCHAR);
        preparedStatement.setBoolean(4,customer.getStatus());
        preparedStatement.setInt(5, customer.getId());
        preparedStatement.executeUpdate();
        ConnectionPool.CloseConnection(connection, preparedStatement, null);
        return customer;
    }

    public boolean delete(Integer id) throws SQLException{
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return true;
    }

    public Customer add(Customer customer) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer "+
        " (name, age, email, status) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setInt(2, customer.getAge());
        if(customer.getEmail() != null)
            preparedStatement.setString(3, customer.getEmail());
        else
            preparedStatement.setNull(3, Types.VARCHAR);
        preparedStatement.setBoolean(4,customer.getStatus());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next()){
            customer.setId(resultSet.getInt(1));
        }
        ConnectionPool.CloseConnection(connection, preparedStatement, resultSet);
        if(customer.getId() == null)
            throw new Exception("Couldn't save customer");
        return customer;
    }
}
