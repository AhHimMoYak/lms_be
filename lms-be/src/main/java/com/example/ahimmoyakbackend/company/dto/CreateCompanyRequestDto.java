package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;

public record CreateCompanyRequestDto(
        Long id,
        String name,
        String owner_name,
        String business_number,
        String email,
        String phone
) {
    public Company toEntity() {
        return Company.builder()
                .name(name)
                .ownerName(owner_name)
                .businessNumber(business_number)
                .email(email)
                .phone(phone)
                .build();
    }

}
