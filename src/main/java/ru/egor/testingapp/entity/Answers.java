package ru.egor.testingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "questions_id")
    private Questions questions_id;

    @Column
    private String text;

    @Column
    private Boolean correct;

    public Long getId() {
        return id;
    }

    public Questions getQuestions_id() {
        return questions_id;
    }

    public String getText() {
        return text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setQuestions_id(Questions questions_id) {
        this.questions_id = questions_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Answers() {
    }

    public Answers(Questions questions_id, String text, Boolean correct) {
        this.questions_id = questions_id;
        this.text = text;
        this.correct = correct;
    }
}
