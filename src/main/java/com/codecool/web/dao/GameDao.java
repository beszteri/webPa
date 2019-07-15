package com.codecool.web.dao;

import com.codecool.web.model.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDao {

    List<Game> findAll() throws SQLException;

    void buyGame(int userId, int gameId) throws SQLException;

    List<Game> findAllPurchasedGameByUserId(int userId) throws SQLException;

    Game findById(int id) throws SQLException;

    void saveGame(String name, String platform, String imageUrl, int price) throws SQLException;

}
