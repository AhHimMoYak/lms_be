package click.ahimmoyak.institutionservice.institution.dto;

import click.ahimmoyak.institutionservice.institution.entity.Institution;
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

}
