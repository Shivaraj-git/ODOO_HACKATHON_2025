package com.BitByBit.ExpenSR.service;

import com.BitByBit.ExpenSR.dto.LoginRequest;
import com.BitByBit.ExpenSR.dto.SignupRequest;
import com.BitByBit.ExpenSR.entity.Company;
import com.BitByBit.ExpenSR.entity.User;
import com.BitByBit.ExpenSR.repository.CompanyRepository;
import com.BitByBit.ExpenSR.repository.UserRepository;
import com.BitByBit.ExpenSR.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        Company company = companyRepository.findByName(request.getCompanyName())
                .orElseGet(() -> companyRepository.save(
                        new Company(
                                null,                             // id (set as null for auto-generation)
                                request.getCompanyName(),         // name
                                "INR",                            // currency (or use your logic)
                                "India",                          // country (or use your logic)
                                java.time.LocalDateTime.now(),    // createdAt (or use null if handled in entity)
                                null                              // users (or new ArrayList<>(), or null if not needed immediately)
                        )
                ));
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(User.Role.ADMIN);
        user.setCompany(company);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(token);
    }
}
