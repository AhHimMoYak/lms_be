package click.ahimmoyak.institutionservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationRequestDto {
    private String password;
}