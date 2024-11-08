package com.example.ahimmoyakbackend.company.dto;

import lombok.Builder;

@Builder
public record SearchCompanyResponseDto(
        Long companyId,
        String companyName
) {
}
