package com.codecool.web.dao.database;

import com.codecool.web.dao.GameDao;
import com.codecool.web.model.Game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                games.add(fetchPlane(resultSet));
            }
            return games;
        }
    }

    private Game fetchPlane(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String platform = resultSet.getString("platform");
        String imageUrl = resultSet.getString("imageUrl");
        int price = resultSet.getInt("price");
        return new Game(id,name, platform, imageUrl, price);
    }
}
