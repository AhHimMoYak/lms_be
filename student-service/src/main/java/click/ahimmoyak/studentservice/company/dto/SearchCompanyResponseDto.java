package click.ahimmoyak.studentservice.company.dto;

import click.ahimmoyak.studentservice.company.entity.Company;
import click.ahimmoyak.studentservice.global.exception.ApiException;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SearchCompanyResponseDto(
        String companyName,
        String emailDomain
) {

    public static SearchCompanyResponseDto from(Company company) {
        String companyEmailDomain = extractDomain(company.getEmail());
        return SearchCompanyResponseDto.builder()
                .companyName(company.getName())
                .emailDomain(companyEmailDomain)
                .build();

    }

    private static String extractDomain(String email) {
        String[] parts = email.split("@");

        if (parts.length == 2) {
            return parts[1].toLowerCase();
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다");
        }
    }



}
