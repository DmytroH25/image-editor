package com.example.imageeditor.controller;

import com.example.imageeditor.dto.LoginRequestDto;
import com.example.imageeditor.dto.LoginResponseDto;
import com.example.imageeditor.dto.RegisterRequestDto;
import com.example.imageeditor.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthServiceImpl authService;

  @PostMapping("/register")
  public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterRequestDto registerRequest) {
    return ResponseEntity.ok(authService.register(registerRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }
}
