package ru.egor.testingapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая тест в системе.
 * Отображается на таблицу 'tests' в базе данных.
 * Содержит информацию о тесте, такую как название, описание, тема, автор и дата создания.
 */
@Entity
@Table(name = "tests")
public class Tests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255, unique = true, nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(length = 255)
    private String theme;

    @ManyToOne
    @JoinColumn(name = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Column()
    private LocalDateTime createDate;

    public Tests() {
    }

    public Tests(String title, String description, String theme, User users_id, LocalDateTime createDate) {
        this.title = title;
        this.description = description;
        this.theme = theme;
        this.author = users_id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
