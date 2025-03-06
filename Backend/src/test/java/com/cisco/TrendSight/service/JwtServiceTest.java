package com.cisco.TrendSight.service;

import org.junit.jupiter.api.*;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup(){
        jwtService = new JwtService();
//        UserDetails dummyUserDetails = new UserDetails();
    }

    @Test
    void generateValidTokenTest(){
        // Need to set up a dummy Userdetails Object

//        jwtService.generateToken();
    }
//    @Test
//    void isTokenExpiredTest(){
//
//    }
}
