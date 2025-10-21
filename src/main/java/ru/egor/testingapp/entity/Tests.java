package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tests")
public class Tests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author")
    private Users author;

    @Column()
    private LocalDateTime created_date;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Users getAuthor() {
        return author;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public Tests() {
    }

    public Tests(String title, String description, Users users_id, LocalDateTime created_date) {
        this.title = title;
        this.description = description;
        this.author = users_id;
        this.created_date = created_date;
    }
}
