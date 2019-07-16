package com.codecool.web.dao.database;

import com.codecool.web.dao.GameDao;
import com.codecool.web.model.Game;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGameDao extends AbstractDao implements GameDao {

    public DatabaseGameDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Game> findAll() throws SQLException {
        String sql = "SELECT * FROM games";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Game> games = new ArrayList<>();
            while (resultSet.next()) {
                games.add(fetchGame(resultSet));
            }
            return games;
        }
    }

    private Game fetchGame(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String platform = resultSet.getString("platform");
        String imageUrl = resultSet.getString("imageUrl");
        int price = resultSet.getInt("price");
        return new Game(id,name, platform, imageUrl, price);
    }

    @Override
    public void buyGame(int userId, int gameId) throws SQLException {
        String sql = "INSERT INTO usersGame(userId, gameId) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            executeInsert(statement);
            connection.commit();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public Game findById(int id) throws SQLException {
        String sql = "SELECT * FROM games WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return fetchGame(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Game> findAllPurchasedGameByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM usersGame WHERE userId=?";
        List<Game> purchasedGames = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    purchasedGames.add(findById(resultSet.getInt("gameId")));
                }
            }
        }
        return purchasedGames;
    }

    @Override
    public void saveGame(String name, String platform, String imageUrl, int price) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO games (name, platform, imageUrl, price) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, platform);
            statement.setString(3, imageUrl);
            statement.setInt(4, price);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}
