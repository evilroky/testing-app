package ru.egor.testingapp.criteria;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Questions;
import ru.egor.testingapp.entity.Tests;

import java.util.List;

@Repository
public class QuestionsRepositoryImpl implements QuestionsRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public QuestionsRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Questions> findByTestsTitle(String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Questions> cq = cb.createQuery(Questions.class);

        Root<Questions> q = cq.from(Questions.class);
        Join<Questions, Tests> t = q.join("test_id", JoinType.INNER);
        Predicate p = cb.equal(t.get("title"),title);

        cq.select(q).where(p);

        return em.createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public void save(Questions questions) {
        if (questions.getId() == null) {
            em.persist(questions);
        }else {
            em.merge(questions);
        }
    }
}
