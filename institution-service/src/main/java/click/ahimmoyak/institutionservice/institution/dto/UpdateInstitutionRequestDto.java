package click.ahimmoyak.institutionservice.institution.dto;

import click.ahimmoyak.institutionservice.institution.entity.Institution;
import click.ahimmoyak.institutionservice.institution.entity.Manager;
import lombok.Builder;

@Builder
public record UpdateInstitutionRequestDto(

    String InstitutionName,
    String address,
    String webSite,
    String description,
    String email,
    String phone
){
}
