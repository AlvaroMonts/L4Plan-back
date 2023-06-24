package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.dto.PlanDTO;
import com.l4plan.api.rest.dto.PlanWithGooglePlacesDTO;
import com.l4plan.api.rest.model.Plan;
import com.l4plan.api.rest.service.PlanService;
import com.l4plan.api.rest.service.PlanSitesGoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plan")
public class PlanController extends GenericController<Plan, PlanDTO> {

    @Autowired
    private PlanService planService;

    @Autowired
    private PlanSitesGoogleService planSitesGoogleService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PlanWithGooglePlacesDTO>> getPlansFromUser(@PathVariable Long id) {

        List<PlanDTO> plans = planService.getPlansByUserId(id);

        List<PlanWithGooglePlacesDTO> result = new ArrayList<>();

        for (PlanDTO plan: plans) {
            PlanWithGooglePlacesDTO planResult = new PlanWithGooglePlacesDTO();
            planResult.setId(plan.getId());
            planResult.setName(plan.getName());
            planResult.setUserId(plan.getUserId());
            planResult.setDescription(plan.getDescription());
            planResult.setPlaces(planSitesGoogleService.getPlacesByPlanId(plan.getId()));
            result.add(planResult);
        }

        return ResponseEntity.ok(result);
    }
}
