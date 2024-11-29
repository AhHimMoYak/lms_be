package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Company;

public record UpdateCompanyRequestDto(
        String name,
        String ownerName,
        String email,
        String phone
) {
    public Company toEntity() {
        return Company.builder()
                .name(name)
                .ownerName(ownerName)
                .email(email)
                .phone(phone)
                .build();
    }
}
