package ru.egor.testingapp.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Users;

import java.util.List;

@Repository
public class UsersRepositoryImpl implements UsersRepositoryCustom {
    private final EntityManager em;

    @Autowired
    public UsersRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<Users> findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);

        Root<Users> root = cq.from(Users.class);
        Predicate predicate = cb.equal(root.get("username"), username);

        cq.select(root).where(predicate);

        return em.createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public void save(Users user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }
}
