package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passed_by")
    private User passedBy;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column
    private Integer score;

    @Column
    private Integer total;

    @Column
    private LocalDateTime passed_date;

    public Long getId() {
        return id;
    }

    public User getPassedBy() {
        return passedBy;
    }

    public Test getTest() {
        return test;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getTotal() {
        return total;
    }

    public LocalDateTime getPassed_date() {
        return passed_date;
    }

    public void setPassedBy(User passedBy) {
        this.passedBy = passedBy;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPassed_date(LocalDateTime passed_date) {
        this.passed_date = passed_date;
    }

    public Result() {
    }

    public Result(User passedBy, Test test, Integer score, Integer total, LocalDateTime passed_date) {
        this.passedBy = passedBy;
        this.test = test;
        this.score = score;
        this.total = total;
        this.passed_date = passed_date;
    }
}
