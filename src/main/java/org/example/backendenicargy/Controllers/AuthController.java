package org.example.backendenicargy.Controllers;


import lombok.Data;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.UserRepository;
import org.example.backendenicargy.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired private UserRepository userRepo;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private org.example.backendenicargy.Security.UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        return jwtService.generateToken(userDetails);
    }

    @Data
    static class LoginRequest {
        private String email;
        private String password;
    }
}
