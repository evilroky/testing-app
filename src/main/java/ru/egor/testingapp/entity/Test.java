package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Column()
    private LocalDateTime createDate;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Test() {
    }

    public Test(String title, String description, User users_id, LocalDateTime createDate) {
        this.title = title;
        this.description = description;
        this.author = users_id;
        this.createDate = createDate;
    }
}
