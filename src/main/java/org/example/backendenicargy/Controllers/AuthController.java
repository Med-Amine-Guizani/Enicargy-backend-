package org.example.backendenicargy.Controllers;


import jakarta.validation.constraints.Null;
import lombok.Data;
import org.example.backendenicargy.Dto.UserDTO;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.UserRepository;
import org.example.backendenicargy.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> register(@RequestBody UserDTO dto) {
        if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }else{
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            user.setUserName(dto.getUsername());
            userRepo.save(user);
            return ResponseEntity.ok(user);
        }

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
