package com.example.ahimmoyakbackend.institution.dto;

import lombok.Builder;

@Builder
public record GetInstitutionDetailRequestDto(
        Long id,
        String institutionName,
        String name,
        String ownerName,
        String businessNumber,
        String certifiedNumber,
        String email,
        String phone
) {

}
