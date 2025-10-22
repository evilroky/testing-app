package ru.egor.testingapp.criteria;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.entity.Test;

import java.util.List;

@Repository
public class QuestionsRepositoryImpl implements QuestionsRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public QuestionsRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Question> findByTestsTitle(String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);

        Root<Question> q = cq.from(Question.class);
        Join<Question, Test> t = q.join("test", JoinType.INNER);
        Predicate p = cb.equal(t.get("title"),title);

        cq.select(q).where(p);

        return em.createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public void save(Question questions) {
        if (questions.getId() == null) {
            em.persist(questions);
        }else {
            em.merge(questions);
        }
    }
}
