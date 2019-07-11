package com.codecool.web.dao;

import com.codecool.web.model.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDao {

    List<Game> findAll() throws SQLException;



}
