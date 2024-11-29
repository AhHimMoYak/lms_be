package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Company;

public record CreateCompanyRequestDto(
        String name,
        String ownerName,
        String businessNumber,
        String email,
        String phone
) {
    public Company toEntity() {
        return Company.builder()
                .name(name)
                .ownerName(ownerName)
                .businessNumber(businessNumber)
                .email(email)
                .phone(phone)
                .build();
    }

}
