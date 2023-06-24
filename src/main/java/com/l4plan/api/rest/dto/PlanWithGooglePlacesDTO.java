package com.l4plan.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanWithGooglePlacesDTO {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private List<PlanSitesGoogleDTO> places;
}