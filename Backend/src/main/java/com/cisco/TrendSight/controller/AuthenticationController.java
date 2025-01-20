package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.dto.LoginAuthorDto;
import com.cisco.TrendSight.dto.RegisterAuthorDto;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final MyUserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(MyUserRepository repository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public MyUser registerUser(@RequestBody RegisterAuthorDto user){
        MyUser myUser = new MyUser(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        return repository.save(myUser);
    }

    @PostMapping("/login")
    public MyUser loginUser(@RequestBody LoginAuthorDto user){
        MyUser myUser = repository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not Found")
            );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    myUser.getEmail(),
                    myUser.getPassword()
                ));
        return myUser;
    }
}
