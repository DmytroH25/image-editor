package com.example.imageeditor.service.impl;

import com.example.imageeditor.repository.UserRepository;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.service.UserService;
import com.example.imageeditor.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}