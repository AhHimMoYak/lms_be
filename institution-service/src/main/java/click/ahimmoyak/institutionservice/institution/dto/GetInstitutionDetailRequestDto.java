package click.ahimmoyak.institutionservice.institution.dto;

import lombok.Builder;

@Builder
public record GetInstitutionDetailRequestDto(
        Long id,
        String institutionName,
        String name,
        String ownerName,
        String businessNumber,
        String certifiedNumber,
        String email,
        String phone
) {

}
