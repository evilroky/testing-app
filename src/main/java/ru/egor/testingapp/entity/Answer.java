package ru.egor.testingapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Сущность, представляющая ответ на вопрос.
 * Этот класс отображается на таблицу "answers" в базе данных.
 * Содержит поля для ID ответа, связанного вопроса, текста ответа и отметки о правильности ответа.
 */
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @Column
    private String text;

    @Column
    private Boolean correct;

    public Answer() {
    }

    public Answer(Question questions_id, String text, Boolean correct) {
        this.question = questions_id;
        this.text = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
