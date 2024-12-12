package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Affiliation;
import lombok.Builder;

@Builder
public record GetEmployeeListResponseDto(
        String username
) {

    public static GetEmployeeListResponseDto from(Affiliation affiliation) {
        return GetEmployeeListResponseDto.builder()
                .username(affiliation.getUser().getUsername())
                .build();
    }
}
