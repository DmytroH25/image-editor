package com.example.imageeditor.service.impl;

import com.example.imageeditor.dto.LoginRequestDto;
import com.example.imageeditor.dto.LoginResponseDto;
import com.example.imageeditor.dto.RegisterRequestDto;
import com.example.imageeditor.repository.RoleRepository;
import com.example.imageeditor.repository.UserRepository;
import com.example.imageeditor.repository.entity.Role;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.service.AuthService;
import com.example.imageeditor.service.exception.RoleNotFoundException;
import com.example.imageeditor.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    return LoginResponseDto.builder()
        .token(jwtUtils.generateToken(authentication))
        .build();
  }

  @Override
  public LoginResponseDto register(RegisterRequestDto registerRequest) {
    User user = User.builder()
        .username(registerRequest.getUsername())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword())).build();
    Role userRole = roleRepository.findByName("ROLE_USER")
        .orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));
    Set<Role> roles = Set.of(userRole);
    user.setRoles(roles);
    User savedUser = userRepository.save(user);
    return login(LoginRequestDto.builder()
        .username(savedUser.getUsername())
        .password(registerRequest.getPassword())
        .build());
  }
}