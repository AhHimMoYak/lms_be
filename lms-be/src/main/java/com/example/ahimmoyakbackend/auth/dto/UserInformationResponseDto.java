package com.example.ahimmoyakbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponseDto {
    private String name;
    private String username;
    private String birth;
    private String phone;
    private String email;
    private String gender;
}
