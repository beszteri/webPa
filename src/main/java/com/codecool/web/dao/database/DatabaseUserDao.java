package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    private User fetchUser(ResultSet resultset) throws SQLException{
        int id = resultset.getInt("id");
        String email = resultset.getString("email");
        String password = resultset.getString("password");
        int balance = resultset.getInt("balance");
        return new User(id, email, password, balance);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(fetchUser(resultSet));
            }
        }

        return null;
    }

    @Override
    public boolean findIfUserExist(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User addUser(String email, String password, int balance) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO users(email, password, balance) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setInt(3, balance);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new User(id, email, password, balance);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void addToBalanceById(int id, int deposit) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE users SET balance=balance+? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, deposit);
            statement.setInt(2, id);
            executeInsert(statement);
            connection.commit();
        }catch (SQLException e){
            connection.rollback();;
            throw e;
        }finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}
