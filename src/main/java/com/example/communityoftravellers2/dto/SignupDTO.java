package com.example.communityoftravellers2.dto;

import com.example.communityoftravellers2.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SignupDTO {
    private String username;
    private String password;
    private Roles roles;
}
