package com.example.ahimmoyakbackend.institution.dto;

import com.example.ahimmoyakbackend.institution.entity.Institution;
import lombok.Builder;

@Builder
public record UserInstitutionIdResponseDto(
        Long institutionId
) {
    public static UserInstitutionIdResponseDto from(Institution institution){
        return UserInstitutionIdResponseDto.builder()
                .institutionId(institution.getId())
                .build();
    }
}
