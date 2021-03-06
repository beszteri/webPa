package com.codecool.web.service;

import com.codecool.web.model.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameService {

    List<Game> findAll() throws SQLException;

    void buyGame(int userId, int gameId) throws SQLException;

    void saveGame(String name, String platform, String imageUrl, int price) throws SQLException;


}
