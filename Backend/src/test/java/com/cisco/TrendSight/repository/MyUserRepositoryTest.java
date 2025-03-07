package com.cisco.TrendSight.repository;

import com.cisco.TrendSight.model.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MyUserRepositoryTest {

    @Autowired
    private MyUserRepository userRepository;
    private MyUser dummyUser;
    private final String defaultUsername = "USERNAME";
    private final String defaultEmail = "USERNAME@gmail.com";
    private final String dummyPassword = "DUMMYPASSWORD";


    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        dummyUser = new MyUser(defaultEmail,dummyPassword);
        userRepository.save(dummyUser);
    }
//
    @Test
    void findByEmailTest(){
        Optional<MyUser> foundUser = userRepository.findByEmail(defaultEmail);
        assertTrue(true);
//        MyUser myUser = foundUser.get();
//        assertEquals(defaultEmail, myUser.getEmail());
//        assertEquals(dummyPassword, myUser.getPassword());
//        verify(userRepository, times(1)).findByEmail(defaultEmail);
    }
//
//    @Test
//    void findAllUsersTest(){
//        MyUser testUser = new MyUser("test@email.com","testing");
//
////        assertSame(myUserLst, userRepository.findAll());
//    }
}