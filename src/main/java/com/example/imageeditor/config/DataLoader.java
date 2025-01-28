package com.example.imageeditor.config;

import com.example.imageeditor.repository.RoleRepository;
import com.example.imageeditor.repository.UserRepository;
import com.example.imageeditor.repository.entity.Role;
import com.example.imageeditor.repository.entity.User;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements
    ApplicationListener<ContextRefreshedEvent> {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
    Role userRole = createRoleIfNotFound("ROLE_USER");
    Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
    if (!userRepository.existsByUsernameOrEmail("dimon4ik", "admin@gmail.com")) {
      User user = User.builder()
          .username("dimon4ik")
          .email("admin@gmail.com")
          .password(passwordEncoder.encode("S0meStr0ngPassw0rd"))
          .roles(Set.of(userRole, adminRole))
          .build();
      userRepository.save(user);
    }
  }

  @Transactional
  Role createRoleIfNotFound(String name) {
    return roleRepository.findByName(name).orElseGet(() -> {
      Role role = Role.builder().name(name).build();
      roleRepository.save(role);
      return role;
    });
  }
}
