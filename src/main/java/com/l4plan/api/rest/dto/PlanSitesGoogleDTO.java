package com.l4plan.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanSitesGoogleDTO {
    private Long id;
    private Long planId;
    // From google api
    private String placeId;
    private String placeName;
    private int position;
}

