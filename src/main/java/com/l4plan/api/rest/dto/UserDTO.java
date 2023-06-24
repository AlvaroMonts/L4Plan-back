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
public class UserDTO {
    private Long id;
    private String pass;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Gender gender;
    private LocalDate birthDate;
}