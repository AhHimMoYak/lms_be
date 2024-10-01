package com.example.ahimmoyakbackend.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentTossRosterResponseDTO {

    private String msg;

    private List<String> userName;

}
