package com.financeAssitant.FinBrains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isNewUser;
}
