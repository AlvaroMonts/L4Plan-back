package com.l4plan.api.rest.dto;

import com.l4plan.api.rest.utils.Gender;
import com.l4plan.api.rest.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDataDTO {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
}