package com.codecool.web.model;

import java.util.Objects;

public final class PersonalInfos extends AbstractModel{

    private String address;
    private String name;
    private String phoneNumber;
    private int userId;

    public PersonalInfos(int id, String address, String name, String phoneNumber, int userId) {
        super(id);
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonalInfos that = (PersonalInfos) o;
        return userId == that.userId &&
            Objects.equals(address, that.address) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, name, phoneNumber, userId);
    }
}
