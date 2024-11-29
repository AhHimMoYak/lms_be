package click.ahimmoyak.companyservice.company.dto;

import java.util.List;

public record submitEmployeeListRequestDto(
        Long courseProvideId,
        List<String> employeeUserName
) {
}
