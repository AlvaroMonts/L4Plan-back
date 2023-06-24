package com.l4plan.api.rest.service;

import com.l4plan.api.rest.dao.PlanDao;
import com.l4plan.api.rest.dto.PlanDTO;
import com.l4plan.api.rest.model.Plan;
import com.l4plan.api.rest.utils.ObjectMapperUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService extends GenericService<Plan, PlanDTO> {

    @Autowired
    private PlanDao planDao;

    public List<PlanDTO> getPlansByUserId(@NonNull Long id) {
        List<Plan> plans = planDao.getAllByUserId(id);
        return ObjectMapperUtils.mapList(plans, PlanDTO.class);
    }
}
