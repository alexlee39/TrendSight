package com.cisco.TrendSight.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role; //For multiple roles: "ADMIN,REVIEWER"
    @OneToMany(mappedBy = "authorUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Article> articlesLst = new ArrayList<>();


    public MyUser(String email, String password, List<Article> articlesLst) {
        this.email = email;
        this.password = password;
        this.articlesLst = articlesLst;
    }

    public MyUser(String email, String password, String role, List<Article> articlesLst) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.articlesLst = articlesLst;
    }

    public MyUser() {

    }

    public MyUser(String email, String password) {
        this.password = password;
        this.email = email;
        this.role = "AUTHOR";
    }

    public MyUser(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
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
    public List<Article> getArticlesLst() { return articlesLst;}

    public void setArticlesLst(List<Article> articlesLst) { this.articlesLst = articlesLst; }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof MyUser myUser)){
            return false;
        }
        return Objects.equals(this.id, myUser.id) && Objects.equals(this.email, myUser.email)
                && Objects.equals(this.password, myUser.password);
    }
}
