package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.dto.PlanSitesGoogleDTO;
import com.l4plan.api.rest.dto.PositionObjectDTO;
import com.l4plan.api.rest.model.PlanSitesGoogle;
import com.l4plan.api.rest.service.PlanSitesGoogleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/plan-sites-google")
public class PlanSitesGoogleController extends GenericController<PlanSitesGoogle, PlanSitesGoogleDTO> {

    @Autowired
    PlanSitesGoogleService planSitesGoogleService;

    @PostMapping("/plan")
    public ResponseEntity<Long> addSiteToPlan(@RequestBody PlanSitesGoogleDTO dto) {
        dto.setPosition(planSitesGoogleService.getLastPositionForPlan(dto.getPlanId())+1);
        Long id = planSitesGoogleService.create(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).body(id);
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<Object> removePlaceFromPlan(@PathVariable Long planId, @RequestBody @NotNull PositionObjectDTO data) {
        planSitesGoogleService.deleteByPlanAndPlacePosition(planId, data.getPosition());
        planSitesGoogleService.updatePositions(planId, data.getPosition());
        return ResponseEntity.noContent().build();
    }

}
