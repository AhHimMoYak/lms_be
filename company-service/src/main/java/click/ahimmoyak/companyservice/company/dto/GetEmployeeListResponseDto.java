package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Affiliation;
import lombok.Builder;

@Builder
public record GetEmployeeListResponseDto(
        String username,
        String name
) {

    public static GetEmployeeListResponseDto from(Affiliation affiliation) {
        return GetEmployeeListResponseDto.builder()
                .username(affiliation.getUser().getUsername())
                .name(affiliation.getUser().getName())
                .build();
    }
}
