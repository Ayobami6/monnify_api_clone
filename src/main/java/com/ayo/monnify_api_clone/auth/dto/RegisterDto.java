package com.ayo.monnify_api_clone.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
