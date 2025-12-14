package ru.egor.testingapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Сущность, представляющая вопрос в тесте.
 * Соответствует таблице "questions" в базе данных.
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tests tests;

    @Column(length = 255)
    private String text;


    public Question(Tests tests, String text) {
        this.tests = tests;
        this.text = text;
    }

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tests getTest() {
        return tests;
    }

    public void setTest(Tests tests_id) {
        this.tests = tests_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
