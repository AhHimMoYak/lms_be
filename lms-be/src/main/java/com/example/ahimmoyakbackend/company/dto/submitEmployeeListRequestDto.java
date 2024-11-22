package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.course.entity.Enrollment;

import java.util.List;

public record submitEmployeeListRequestDto(
        Long courseProvideId,
        List<String> employeeUserName
) {
}
