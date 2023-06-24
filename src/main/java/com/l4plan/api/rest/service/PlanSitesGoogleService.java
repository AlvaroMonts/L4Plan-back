package com.l4plan.api.rest.service;

import com.l4plan.api.rest.dao.PlanSitesGoogleDao;
import com.l4plan.api.rest.dto.PlanSitesGoogleDTO;
import com.l4plan.api.rest.model.PlanSitesGoogle;
import com.l4plan.api.rest.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanSitesGoogleService extends GenericService<PlanSitesGoogle, PlanSitesGoogleDTO> {

    @Autowired
    PlanSitesGoogleDao planSitesGoogleDao;

    public int getLastPositionForPlan(Long planId) {
        return planSitesGoogleDao.getLastPositionForPlan(planId);
    }

    public void deleteByPlanAndPlacePosition(Long planId, int position) {
        planSitesGoogleDao.deleteByPlanAndPlacePosition(planId, position);
    }

    public void updatePositions(Long planId, int positionDeleted) {
        planSitesGoogleDao.updatePositionsForPlan(planId,positionDeleted);
    }

    public List<PlanSitesGoogleDTO> getPlacesByPlanId(Long planId) {
        return ObjectMapperUtils.mapList(planSitesGoogleDao.getPlacesByPlanId(planId), PlanSitesGoogleDTO.class);
    }
}

