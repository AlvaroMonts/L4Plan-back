package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.dto.PlanSitesGoogleDTO;
import com.l4plan.api.rest.dto.PlanWithGooglePlacesDTO;
import com.l4plan.api.rest.dto.PositionObjectDTO;
import com.l4plan.api.rest.model.PlanSitesGoogle;
import com.l4plan.api.rest.service.PlanSitesGoogleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plan-sites-google")
public class PlanSitesGoogleController extends GenericController<PlanSitesGoogle, PlanSitesGoogleDTO> {

    @Autowired
    PlanSitesGoogleService planSitesGoogleService;

    @PostMapping("/plan")
    public ResponseEntity<Long> addSiteToPlan(@RequestBody PlanSitesGoogleDTO dto) {
        dto.setPosition(planSitesGoogleService.getLastPositionForPlan(dto.getPlanId())+1);
        return ResponseEntity.ok(planSitesGoogleService.create(dto));
    }

    @DeleteMapping("/plan/{planId}")
    public void removePlaceFromPlan(@PathVariable Long planId, @RequestBody @NotNull PositionObjectDTO data) {
        planSitesGoogleService.deleteByPlanAndPlacePosition(planId, data.getPosition());
        planSitesGoogleService.updatePositions(planId, data.getPosition());
    }

}
