package com.example.ahimmoyakbackend.company.dto;

import lombok.Builder;

@Builder
public record CompanyDetailResponseDto(
        String companyName,
        String ownerName,
        String businessNumber,
        String email,
        String phone
) {

}
