package com.example.ahimmoyakbackend.company.dto;

import lombok.Builder;

@Builder
public record GetEmployeeListResponseDto(
        String username,
        String name
) {

}
