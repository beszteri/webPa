package com.codecool.web.dao;

import com.codecool.web.model.PersonalInfos;

import java.sql.SQLException;
import java.util.List;

public interface PersonalInfosDao {

    List<PersonalInfos> findAll() throws SQLException;

    PersonalInfos findByUserId(int id) throws SQLException;

    PersonalInfos addPersonalInfos(String address, String name, String phoneNumber, int userId) throws SQLException;

    void updateAddressById(int id, String address) throws SQLException;

    void updateNameById(int id, String name) throws SQLException;

    void updatePhoneNumberById(int id, String phoneNumber) throws SQLException;

    boolean findIfPersonalInfosExist(int id) throws SQLException;

}
