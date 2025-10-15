package ru.egor.testingapp.entity;

import java.util.ArrayList;
import java.util.List;

public class TestEntity {
    private Long id;
    private String title;
    private String subject;
    private List<String> questions = new ArrayList<>();


    public TestEntity(Long id, String title, String subject) {
        this.id = id;
        this.title = title;
        this.subject = subject;
    }

    public void addQuestion(String qAndA) {
        this.questions.add(qAndA);
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubject() {
        return this.subject;
    }

    public List<String> getQuestions() {
        return this.questions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}
