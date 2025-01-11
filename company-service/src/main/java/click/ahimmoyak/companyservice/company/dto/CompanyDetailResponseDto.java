package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Company;
import lombok.Builder;

@Builder
public record CompanyDetailResponseDto(
        Long id,
        String companyName,
        String ownerName,
        String businessNumber,
        String email,
        String phone
) {
    public CompanyDetailResponseDto of(Company company) {
        return CompanyDetailResponseDto.builder()
                .id(company.getId())
                .companyName(company.getName())
                .ownerName(company.getOwnerName())
                .businessNumber(company.getBusinessNumber())
                .email(company.getEmail())
                .phone(company.getPhone())
                .build();
    }
}
