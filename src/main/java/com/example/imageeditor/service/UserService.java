package com.example.imageeditor.service;

import com.example.imageeditor.repository.entity.User;

public interface UserService {
  User getUserByUsername(String username);
}