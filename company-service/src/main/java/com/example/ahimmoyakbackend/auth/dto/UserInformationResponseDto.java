package com.example.ahimmoyakbackend.auth.dto;

import com.example.ahimmoyakbackend.auth.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponseDto {
    private String name;
    private String username;
    private LocalDate birth;
    private String phone;
    private String email;
    private Gender gender;
}
