package click.ahimmoyak.studentservice.auth.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityExceptionDto {
    private int statusCode;
    private String msg;
}
