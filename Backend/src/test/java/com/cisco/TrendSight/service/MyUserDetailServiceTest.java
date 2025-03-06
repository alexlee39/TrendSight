package com.cisco.TrendSight.service;

import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyUserDetailServiceTest {

    @InjectMocks
    private MyUserDetailService myUserDetailService;
    @Mock
    private MyUserRepository userRepository;
    private MyUser dummyUser;
    private final String defaultUsername = "USERNAME";
    private final String defaultEmail = "USERNAME@gmail.com";
    private final String dummyPassword = "DUMMYPASSWORD";
    private final int defaultID = 1;


    @BeforeEach
    void setUp(){
//        myUserDetailService = new MyUserDetailService(userRepository);
        dummyUser = new MyUser(defaultEmail,dummyPassword);
        userRepository.save(dummyUser);
        myUserDetailService = new MyUserDetailService(userRepository);

    }

    @Test
    void myUserRepositoryTest(){
        when(userRepository.findByEmail(defaultEmail)).thenReturn(Optional.of(dummyUser));
        Optional<MyUser> foundUser = userRepository.findByEmail(defaultEmail);
        assertTrue(foundUser.isPresent());
        MyUser myUser = foundUser.get();
        assertEquals(defaultEmail, myUser.getEmail());
        assertEquals(dummyPassword, myUser.getPassword());

//        assertInstanceOf(dummyUser, myUserDetailService.getUserFromEmail(defaultEmail));
//        assertEquals(dummyUser, myUserDetailService.getUserFromEmail(defaultEmail));
    }

    @Test
    void getRoles() {
    }

    @Test
    void getUserFromEmail() {
    }

    @Test
    void getUserFromId() {
    }
}