package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(length = 25)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;

    @Column()
    public LocalDateTime createDate;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRoleId() {
        return roleId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public void setCreate_date(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public User() {
    }

    public User(String username, String password, Role roleId, LocalDateTime createDate) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.createDate = createDate;
    }
}
