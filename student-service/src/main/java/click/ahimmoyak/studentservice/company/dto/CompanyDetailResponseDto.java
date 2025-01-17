package click.ahimmoyak.studentservice.company.dto;

import click.ahimmoyak.studentservice.company.entity.Company;
import lombok.Builder;

@Builder
public record CompanyDetailResponseDto(
        String companyName,
        String ownerName,
        String businessNumber,
        String email,
        String phone
) {
    public CompanyDetailResponseDto of(Company company) {
        return CompanyDetailResponseDto.builder()
                .companyName(company.getName())
                .ownerName(company.getOwnerName())
                .businessNumber(company.getBusinessNumber())
                .email(company.getEmail())
                .phone(company.getPhone())
                .build();
    }
}
