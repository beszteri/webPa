package com.codecool.web.dao.database;

import com.codecool.web.dao.PersonalInfosDao;
import com.codecool.web.model.PersonalInfos;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabasePersonalInfosDao extends AbstractDao implements PersonalInfosDao {

    public DatabasePersonalInfosDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<PersonalInfos> findAll() throws SQLException {
        String sql = "SELECT * FROM userInfos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<PersonalInfos> personalInfos = new ArrayList<>();
            while (resultSet.next()) {
                personalInfos.add(fetchUser(resultSet));
            }
            return personalInfos;
        }
    }

    private PersonalInfos fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String address = resultSet.getString("address");
        String name = resultSet.getString("name");
        String phoneNumber = resultSet.getString("phoneNumber");
        int userId = resultSet.getInt("userId");
        return new PersonalInfos(id, address, name, phoneNumber, userId);
    }

    @Override
    public PersonalInfos findByUserId(int id) throws SQLException {
        String sql = "SELECT * FROM userInfos WHERE userId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public PersonalInfos addPersonalInfos(String address, String name, String phoneNumber, int userId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO userInfos(address, name, phoneNumber, userId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, address);
            statement.setString(2, name);
            statement.setString(3, phoneNumber);
            statement.setInt(4, userId);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new PersonalInfos(id, address, name, phoneNumber, userId);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void updateAddressById(int id, String address) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE userInfos SET address=? WHERE userId =?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, address);
            statement.setInt(2, id);
            executeInsert(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void updateNameById(int id, String name) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE userInfos SET name=? WHERE userId =?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            executeInsert(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void updatePhoneNumberById(int id, String phoneNumber) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE userInfos SET phoneNumber=? WHERE userId =?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, phoneNumber);
            statement.setInt(2, id);
            executeInsert(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public boolean findIfPersonalInfosExist(int id) throws SQLException {
        String sql = "SELECT * FROM userInfos WHERE userId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }
}
