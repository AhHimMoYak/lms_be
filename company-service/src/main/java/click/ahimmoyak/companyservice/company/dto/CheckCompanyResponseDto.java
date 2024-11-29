package click.ahimmoyak.companyservice.company.dto;

import lombok.Builder;

@Builder
public record CheckCompanyResponseDto(
        String message,
        Boolean success
) {

}
