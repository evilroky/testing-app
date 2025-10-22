package ru.egor.testingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test")
    private Test test;

    @Column(length = 255)
    private String text;



    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public String getText() {
        return text;
    }


    public void setTest_id(Test test_id) {
        this.test = test_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question(Test test, String text) {
        this.test = test;
        this.text = text;
    }

    public Question() {
    }
}
