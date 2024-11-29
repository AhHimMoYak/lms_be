package click.ahimmoyak.companyservice.auth.dto;

import click.ahimmoyak.companyservice.auth.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponseDto {
    private String name;
    private String username;
    private LocalDate birth;
    private String phone;
    private String email;
    private Gender gender;
}
