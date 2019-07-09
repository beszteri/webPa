package com.codecool.web.model;

import java.util.Objects;

public final class User extends AbstractModel {

    private String email;
    private String password;
    private int balance;

    public User(int id, String email, String password, int balance) {
        super(id);
        this.email = email;
        this.password = password;
        this.balance = balance;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return balance == user.balance &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, balance);
    }
}
