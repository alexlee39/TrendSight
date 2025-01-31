package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.dto.LoginAuthorDto;
import com.cisco.TrendSight.dto.RegisterAuthorDto;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import com.cisco.TrendSight.service.JwtService;
import com.cisco.TrendSight.service.MyUserDetailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
public class AuthenticationController {

    @Value("${security.jwt.expiration-time}")
    private long jwtTokenAge;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;
    private final MyUserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(JwtService jwtService, MyUserDetailService myUserDetailService, MyUserRepository repository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.myUserDetailService = myUserDetailService;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<MyUser> registerUser(@RequestBody RegisterAuthorDto user){
        MyUser myUser = new MyUser(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        if(repository.findByEmail(user.getEmail()).isPresent()){
            return new ResponseEntity<>(myUser,HttpStatus.CONFLICT);
        }
        repository.save(myUser);
        return new ResponseEntity<>(myUser, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public List<MyUser> findAllUsers(){
        return repository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @GetMapping("/role")
    public ResponseEntity<String> findOwnRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String role = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("No Roles Assigned");
            return ResponseEntity.ok(role);
        }
        else{
            return new ResponseEntity<String>("User not Authenticated", HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<MyUser> loginUser(@RequestBody LoginAuthorDto user, HttpServletResponse response){
        // Offset tokenAge from seconds to milliseconds for maxAge property
        long jwtTokenAgeSecs = jwtTokenAge / 1000;
        if (repository.findByEmail(user.getEmail()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{

            String jwtToken = jwtService.generateToken(myUserDetailService.loadUserByUsername(user.getEmail()));
            ResponseCookie responseCookie = ResponseCookie
                    .from("JWT",jwtToken)
                    .secure(true)
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .maxAge(jwtTokenAgeSecs)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            MyUser myUser = repository.findByEmail(user.getEmail()).get();
            return ResponseEntity.ok(myUser);
        }
        catch(BadCredentialsException exception){
//            exception.getMessage() just prints "Bad Credentials"
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
