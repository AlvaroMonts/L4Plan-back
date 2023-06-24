package com.l4plan.api.rest.dao;

import com.l4plan.api.rest.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

    @Transactional
    public boolean existsUserByEmail(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Long> query = currentSession.createQuery("Select count(u) from User u where u.email = :email", Long.class).setParameter("email", email);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public List<User> findByEmail(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from User u where u.email = :email";
        Query<User> query = currentSession.createQuery(hql, User.class).setParameter("email", email);
        return query.getResultList();
    }
}
