package com.cisco.TrendSight.service;

import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findByEmail(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException(email);
        }
        var userObj = user.get();
        return User.builder()
                .username(userObj.getEmail())
                .password(userObj.getPassword())
                .roles(getRoles(userObj))
                .build();
    }

    public String[] getRoles(MyUser user){
        if(user.getRole() == null){
            return new String[]{}; // If no Roles, getRole should be empty?
        }
        return user.getRole().split(",");
    }

    public MyUser getUserFromEmail(String email){
        if(repository.findByEmail(email).isPresent()){
            return repository.findByEmail(email).get();
        }
        throw new UsernameNotFoundException(email);
    }

    public MyUser getUserFromId(Long id){
        if(repository.findById(id).isPresent()){
            return repository.findById(id).get();
        }
        throw new RuntimeException("User with id " + id + "not found");
    }
}
