package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;

public record UpdateCompanyRequestDto(
        Long id,
        String name,
        String owner_name,
        String business_number,
        String email,
        String phone
) {
    public Company toEntity() {
        return Company.builder()
                .id(id)
                .name(name)
                .ownerName(owner_name)
                .businessNumber(business_number)
                .email(email)
                .phone(phone)
                .build();
    }
}
