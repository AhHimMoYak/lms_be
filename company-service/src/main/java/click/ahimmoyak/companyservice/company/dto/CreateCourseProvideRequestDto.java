package click.ahimmoyak.companyservice.company.dto;

import java.time.LocalDate;

public record CreateCourseProvideRequestDto(
        LocalDate beginDate,
        LocalDate endDate,
        int attendeeCount,
        long deposit
) {
}
