package com.example.communityoftravellers2.dto;

import com.example.communityoftravellers2.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    @JsonProperty("token")
    private String token;
    private User user;
}
