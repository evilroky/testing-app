package ru.egor.testingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question")
    private Question question;

    @Column
    private String text;

    @Column
    private Boolean correct;

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getText() {
        return text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Answer() {
    }

    public Answer(Question questions_id, String text, Boolean correct) {
        this.question = questions_id;
        this.text = text;
        this.correct = correct;
    }
}
