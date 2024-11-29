package click.ahimmoyak.companyservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeJoinRequestDto {
    private String departmentId;
    private String companyId;
}
