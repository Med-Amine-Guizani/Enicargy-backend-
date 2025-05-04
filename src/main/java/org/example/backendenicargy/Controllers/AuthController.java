package org.example.backendenicargy.Controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.extern.java.Log;
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
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<LoginResponse> register( @RequestBody UserDTO dto) {
        System.out.println(dto);
        if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
            System.out.println("Email already exists");
            return ResponseEntity.badRequest().build();
        }else{
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            user.setUserName(dto.getUsername());
            userRepo.save(user);


            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(token));

        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }
    @Data
    public class LoginResponse {
        private String accessToken;

        public LoginResponse(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    @Data
    static class LoginRequest {
        private String email;
        private String password;
    }
}
