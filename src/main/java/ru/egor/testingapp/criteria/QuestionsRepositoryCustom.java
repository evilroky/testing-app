package ru.egor.testingapp.criteria;

import ru.egor.testingapp.entity.Questions;

import java.util.List;

public interface QuestionsRepositoryCustom {

    //Нахождения теста по названию
    List<Questions> findByTestsTitle(String title);

    void save(Questions questions);
}
