package click.ahimmoyak.companyservice.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentConfirmRequestDto {

    private List<Long> enrollmentList;

}
