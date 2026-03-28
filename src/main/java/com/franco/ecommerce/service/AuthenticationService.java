package com.franco.ecommerce.service;

import com.franco.ecommerce.dto.AuthenticationRequest;
import com.franco.ecommerce.dto.AuthenticationResponse;
import com.franco.ecommerce.dto.RegisterRequest;
import com.franco.ecommerce.enums.Role;
import com.franco.ecommerce.model.Customer;
import com.franco.ecommerce.repository.CustomerRepository;
import com.franco.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = customerRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        return registerWithRole(request, Role.ADMIN);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        return registerWithRole(request, Role.USER);
    }

    private AuthenticationResponse registerWithRole(RegisterRequest request, Role role) {
        var user = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dni(request.getDni())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        customerRepository.save(user);
        var  jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
