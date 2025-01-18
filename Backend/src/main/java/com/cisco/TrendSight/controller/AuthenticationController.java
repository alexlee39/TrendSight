package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    private final MyUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(MyUserRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    // Consider using DTO: to simplify data being passed?
    @PostMapping("/register")
    public MyUser registerUser(@RequestBody MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    // For Testing/Admin Use Only
    @GetMapping("/user")
    public List<MyUser> findAll(){
        return repository.findAll();
    }


//    @PostMapping("/login")
//    public MyUser loginUser(@RequestBody MyUser user){
//        if (repository.findByEmail(user.getEmail()).isEmpty()){
//            throw new UsernameNotFoundException(user.getEmail());
//        }
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//
//                ))
//    }
}
