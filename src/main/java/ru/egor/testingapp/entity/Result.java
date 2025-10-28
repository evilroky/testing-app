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
    private LocalDateTime passedDate;

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

    public LocalDateTime getPassedDate() {
        return passedDate;
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

    public void setPassedDate(LocalDateTime passedDate) {
        this.passedDate = passedDate;
    }

    public Result() {
    }

    public Result(User passedBy, Test test, Integer score, Integer total, LocalDateTime passedDate) {
        this.passedBy = passedBy;
        this.test = test;
        this.score = score;
        this.total = total;
        this.passedDate = passedDate;
    }
}
