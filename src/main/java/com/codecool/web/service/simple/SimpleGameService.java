package com.codecool.web.service.simple;

import com.codecool.web.dao.GameDao;
import com.codecool.web.model.Game;
import com.codecool.web.service.GameService;

import java.sql.SQLException;
import java.util.List;

public final class SimpleGameService implements GameService {

    private final GameDao gameDao;

    public SimpleGameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        return gameDao.findAll();
    }

    @Override
    public void buyGame(int userId, int gameId) throws SQLException {
        gameDao.buyGame(userId, gameId);
    }
}
