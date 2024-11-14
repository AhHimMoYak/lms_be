package com.example.ahimmoyakbackend.company.dto;

import lombok.Builder;

@Builder
public record CheckCompanyResponseDto(
        String message,
        Boolean success
) {

}
