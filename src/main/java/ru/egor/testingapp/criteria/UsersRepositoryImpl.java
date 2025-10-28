package ru.egor.testingapp.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.User;

import java.util.List;

@Repository
public class UsersRepositoryImpl implements UsersRepositoryCustom {
    private final EntityManager em;

    @Autowired
    public UsersRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<User> findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> root = cq.from(User.class);
        Predicate predicate = cb.equal(root.get("username"), username);

        cq.select(root).where(predicate);

        return em.createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }
}
