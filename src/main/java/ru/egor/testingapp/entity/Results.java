package ru.egor.testingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class Results {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passed_by")
    private Users passed_by;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Tests test_id;

    @Column
    private Integer score;

    @Column
    private Integer total;

    @Column
    private LocalDateTime passed_date;

    public Long getId() {
        return id;
    }

    public Users getPassed_by() {
        return passed_by;
    }

    public Tests getTest_id() {
        return test_id;
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

    public void setPassed_by(Users passed_by) {
        this.passed_by = passed_by;
    }

    public void setTest_id(Tests test_id) {
        this.test_id = test_id;
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

    public Results() {
    }

    public Results(Users user_id, Tests test_id, Integer score, Integer total, LocalDateTime passed_date) {
        this.passed_by = user_id;
        this.test_id = test_id;
        this.score = score;
        this.total = total;
        this.passed_date = passed_date;
    }
}
