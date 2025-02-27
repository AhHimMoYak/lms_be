package click.ahimmoyak.institutionservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationRequestDto {
    private String password;
    private String phone;
    private String email;
}
