package com.codecool.web.model;

public enum Role {
    ADMIN("admin"),
    REGISTERED("registered"),
    UNREGISTERED("unregistered");

    private final String value;

    private Role(String value){this.value = value;}

    public String getValue(){return value;}
}
