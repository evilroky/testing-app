package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(length = 25)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role_id;

    @Column()
    public LocalDateTime create_date;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole_id() {
        return role_id;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole_id(Roles role_id) {
        this.role_id = role_id;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public Users() {
    }

    public Users(String username, String password, Roles role_id, LocalDateTime create_date) {
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.create_date = create_date;
    }
}
