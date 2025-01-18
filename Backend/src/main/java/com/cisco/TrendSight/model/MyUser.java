package com.cisco.TrendSight.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
//@Table(name = "user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role; //For multiple roles: "ADMIN,REVIEWER"
//    private boolean enabled;
//    @Column(name = "verification-code")
//    private String verificationCode;
//    @Column(name = "verification=expiration")
//    private LocalDateTime verificationCodeExpiredAt;

    public MyUser() {
    }

    public MyUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "AUTHOR";
    }

    public MyUser(String password, String email, String role) {
        this.password = password;
        this.email = email;
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

//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//      Should initialize AUTHOR, REVIEWER, ADMIN authorities.
//        return List.of();
//    }
//    @Override
//    public boolean isAccountNonExpired(){
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked(){
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonExpired(){
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked(){
//        return true;
}
