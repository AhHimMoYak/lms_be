package click.ahimmoyak.studentservice.institution.dto;

import click.ahimmoyak.studentservice.institution.entity.Institution;
import lombok.Builder;

@Builder
public record CreateInstitutionRequestDto(
        String name,
        String ownerName,
        String businessNumber,
        String certifiedNumber,
        String email,
        String phone
){
    public static CreateInstitutionRequestDto from(Institution institution){
        return CreateInstitutionRequestDto.builder()
                .name(institution.getName())
                .ownerName(institution.getOwnerName())
                .businessNumber(institution.getBusinessNumber())
                .certifiedNumber(institution.getCertifiedNumber())
                .email(institution.getEmail())
                .phone(institution.getPhone())
                .build();
    }

}
