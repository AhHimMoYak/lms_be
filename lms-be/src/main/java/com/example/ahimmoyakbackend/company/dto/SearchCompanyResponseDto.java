package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;
import lombok.Builder;

@Builder
public record SearchCompanyResponseDto(
        Long companyId,
        String companyName
) {

    public static SearchCompanyResponseDto from(Company company) {
        return SearchCompanyResponseDto.builder()
                .companyId(company.getId())
                .companyName(company.getName())
                .build();

    }


}
