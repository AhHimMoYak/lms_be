package click.ahimmoyak.institutionservice.institution.dto;

import click.ahimmoyak.institutionservice.institution.entity.Institution;
import click.ahimmoyak.institutionservice.institution.entity.Manager;
import lombok.Builder;

@Builder
public record UpdateInstitutionRequestDto(

    String name,
    String ownerName,
    String businessNumber,
    String certifiedNumber,
    String email,
    String phone
){
}
