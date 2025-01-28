package com.example.imageeditor.service;

import com.example.imageeditor.dto.LoginRequestDto;
import com.example.imageeditor.dto.LoginResponseDto;
import com.example.imageeditor.dto.RegisterRequestDto;

public interface AuthService {
  LoginResponseDto login(LoginRequestDto loginRequest);
  LoginResponseDto register(RegisterRequestDto registerRequest);
}