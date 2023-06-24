package com.l4plan.api.rest.dao;

import com.l4plan.api.rest.model.Plan;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlanDao extends GenericDao<Plan> {

    @Transactional
    public List<Plan> getAllByUserId(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String query = "select * from plan where user_id in (:id) order by created_at asc";
        return currentSession.createNativeQuery(query, Plan.class).setParameter("id", id).getResultList();
    }
}
