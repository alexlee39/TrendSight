package com.cisco.TrendSight.model;

import jakarta.persistence.*;


@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role; //For multiple roles: "ADMIN,REVIEWER"

    public MyUser() {

    }

    public MyUser(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUser(String email, String password) {
        this.password = password;
        this.email = email;
        this.role = "AUTHOR";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
