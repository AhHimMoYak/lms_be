package com.example.ahimmoyakbackend.institution.dto;

import com.example.ahimmoyakbackend.institution.entity.Institution;
import lombok.Builder;

@Builder
public record CreateInstitutionResponseDto(
        Long id,
        String name,
        String ownerName,
        String businessNumber,
        String certifiedNumber,
        String email,
        String phone
){
    public static CreateInstitutionResponseDto from(Institution institution){
        return CreateInstitutionResponseDto.builder()
                .name(institution.getName())
                .ownerName(institution.getOwnerName())
                .businessNumber(institution.getBusinessNumber())
                .certifiedNumber(institution.getCertifiedNumber())
                .email(institution.getEmail())
                .phone(institution.getPhone())
                .build();
    }

}
