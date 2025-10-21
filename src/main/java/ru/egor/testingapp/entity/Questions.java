package ru.egor.testingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Tests test_id;

    @Column(length = 255)
    private String text;



    public Long getId() {
        return id;
    }

    public Tests getTest_id() {
        return test_id;
    }

    public String getText() {
        return text;
    }


    public void setTest_id(Tests test_id) {
        this.test_id = test_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Questions(Tests tests_id, String text) {
        this.test_id = tests_id;
        this.text = text;
    }

    public Questions() {
    }
}
