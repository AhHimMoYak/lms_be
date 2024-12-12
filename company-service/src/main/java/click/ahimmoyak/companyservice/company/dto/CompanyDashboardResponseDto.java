package click.ahimmoyak.companyservice.company.dto;

import lombok.Builder;

@Builder
public record CompanyDashboardResponseDto(
        int employeeCount,
        int ongoingCount,
        int pendingCount
) {

}
