package com.codecool.web.model;

import java.util.Objects;

public final class User extends AbstractModel {

    private String email;
    private String password;
    private int balance;
    private Role role;

    public User(int id, String email, String password, int balance, Role role) {
        super(id);
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return balance == user.balance &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password) &&
            role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, balance, role);
    }
}
