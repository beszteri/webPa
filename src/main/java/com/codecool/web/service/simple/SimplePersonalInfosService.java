package com.codecool.web.service.simple;

import com.codecool.web.dao.PersonalInfosDao;
import com.codecool.web.model.PersonalInfos;
import com.codecool.web.service.PersonalInfosService;

import java.sql.SQLException;
import java.util.List;

public final class SimplePersonalInfosService implements PersonalInfosService {

    private final PersonalInfosDao personalInfosDao;

    public SimplePersonalInfosService(PersonalInfosDao personalInfosDao) {
        this.personalInfosDao = personalInfosDao;
    }

    @Override
    public PersonalInfos findByUserId(int id) throws SQLException {
        return personalInfosDao.findByUserId(id);
    }

    @Override
    public PersonalInfos addPersonalInfos(String address, String name, String phoneNumber, int userId) throws SQLException {
        return personalInfosDao.addPersonalInfos(address, name, phoneNumber, userId);
    }

    @Override
    public void updateAddressById(int id, String address) throws SQLException {
        personalInfosDao.updateAddressById(id, address);
    }

    @Override
    public void updateNameById(int id, String name) throws SQLException {
        personalInfosDao.updateNameById(id, name);
    }

    @Override
    public void updatePhoneNumberById(int id, String phoneNumber) throws SQLException {
        personalInfosDao.updatePhoneNumberById(id, phoneNumber);
    }

    @Override
    public List<PersonalInfos> findAll() throws SQLException {
        return personalInfosDao.findAll();
    }

    @Override
    public boolean findIfPersonalInfosExist(int id) throws SQLException {
        return personalInfosDao.findIfPersonalInfosExist(id);
    }
}
