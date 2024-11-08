package com.example.ahimmoyakbackend.institution.dto;

import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import lombok.Builder;

@Builder
public record CreateInstitutionRequestDto(
    String name,
    String ownerName,
    String businessNumber,
    String certifiedNumber,
    String email,
    String phone,
    Manager manager
){
    public static CreateInstitutionRequestDto from(Institution institution){
        return CreateInstitutionRequestDto.builder()
                .build();
    }

}
