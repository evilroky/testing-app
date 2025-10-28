package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.repository.QuestionRepository;

import java.util.List;

@Controller
@RequestMapping("/main/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @Autowired
    QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/findByTestsTitle/{title}")
    public List<Question> findByTestsTitle (@PathVariable String title) {
        return questionRepository.findByTestsTitle(title);
    }


}
