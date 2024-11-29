package click.ahimmoyak.studentservice.company.dto;

import java.util.List;

public record submitEmployeeListRequestDto(
        Long courseProvideId,
        List<String> employeeUserName
) {
}
