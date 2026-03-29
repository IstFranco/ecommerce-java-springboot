package com.franco.ecommerce.dto;

import com.franco.ecommerce.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String dni;
    private Role role;
}