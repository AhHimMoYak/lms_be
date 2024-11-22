package com.example.ahimmoyakbackend.company.dto;

import java.util.List;

public record submitEmployeeListRequestDto(
        Long courseProvideId,
        List<String> employeeUserName
) {
}
