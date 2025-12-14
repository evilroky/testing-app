package ru.egor.testingapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая результат прохождения теста.
 * Отображается на таблицу "results" в базе данных.
 * Содержит информацию о попытке прохождения теста пользователем, включая балл, количество вопросов и дату прохождения.
 */
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passed_by")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User passedBy;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tests tests;

    @Column
    private Integer score;

    @Column
    private Integer total;

    @Column
    private LocalDateTime passedDate;

    public Result() {
    }

    public Result(User passedBy, Tests tests, Integer score, Integer total, LocalDateTime passedDate) {
        this.passedBy = passedBy;
        this.tests = tests;
        this.score = score;
        this.total = total;
        this.passedDate = passedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPassedBy() {
        return passedBy;
    }

    public void setPassedBy(User passedBy) {
        this.passedBy = passedBy;
    }

    public Tests getTest() {
        return tests;
    }

    public void setTest(Tests tests) {
        this.tests = tests;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDateTime getPassedDate() {
        return passedDate;
    }

    public void setPassedDate(LocalDateTime passedDate) {
        this.passedDate = passedDate;
    }
}
