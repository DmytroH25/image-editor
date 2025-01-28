package com.example.imageeditor.service;

import com.example.imageeditor.model.UserDetailsImpl;
import com.example.imageeditor.repository.UserRepository;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(usernameOrEmail));
        return UserDetailsImpl.build(user);
    }
}
