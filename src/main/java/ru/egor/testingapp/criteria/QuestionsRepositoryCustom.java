package ru.egor.testingapp.criteria;

import ru.egor.testingapp.entity.Question;

import java.util.List;

public interface QuestionsRepositoryCustom {

    //Нахождения теста по названию
    List<Question> findByTestsTitle(String title);

    void save(Question questions);
}
