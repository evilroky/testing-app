package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая пользователя в системе.
 * Связана с таблицей "users" в базе данных.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column
    private LocalDateTime createDate;

    public User() {
    }

    public User(String username, String password, Role role, LocalDateTime createDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
