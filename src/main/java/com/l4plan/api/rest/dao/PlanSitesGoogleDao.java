package com.l4plan.api.rest.dao;

import com.l4plan.api.rest.model.PlanSitesGoogle;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlanSitesGoogleDao extends GenericDao<PlanSitesGoogle> {

    @Transactional
    public int getLastPositionForPlan(Long planId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Integer> query = currentSession.createQuery("select max(position) from PlanSitesGoogle where plan.id = :planId", Integer.class);
        query.setParameter("planId", planId);
        Integer result = query.getSingleResult();

        if (result == null) {
            return 0;
        } else {
            return result;
        }
    }

    @Transactional
    public void deleteByPlanAndPlacePosition(Long planId, int position) {
        entityManager.createQuery( "DELETE FROM PlanSitesGoogle ps WHERE ps.plan.id = :planId AND position = :position")
                .setParameter("planId", planId)
                .setParameter("position", position)
                .executeUpdate();
    }


    @Transactional
    public void updatePositionsForPlan(Long planId, int positionDeleted) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<PlanSitesGoogle> query = currentSession.createQuery("FROM PlanSitesGoogle WHERE plan.id = :planId AND position > :positionDeleted", PlanSitesGoogle.class);
        query.setParameter("planId", planId);
        query.setParameter("positionDeleted", positionDeleted);
        List<PlanSitesGoogle> planSitesGoogle = query.getResultList();

        for (PlanSitesGoogle planSiteGoogle : planSitesGoogle) {
            planSiteGoogle.setPosition(planSiteGoogle.getPosition() - 1);
            entityManager.merge(planSiteGoogle);
        }
    }

    public List<PlanSitesGoogle> getPlacesByPlanId(Long planId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<PlanSitesGoogle> query = currentSession.createQuery("from PlanSitesGoogle where plan.id = :planId", PlanSitesGoogle.class);
        return query.setParameter("planId", planId).getResultList();
    }
}
