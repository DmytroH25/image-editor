package com.example.imageeditor.repository.entity;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Image image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_user_roles_user_id",
                foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE")),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_user_roles_role_id",
                foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE"))
    )
    private Collection<Role> roles;
}
