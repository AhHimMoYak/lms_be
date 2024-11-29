package click.ahimmoyak.studentservice.auth.entity;

import click.ahimmoyak.studentservice.auth.common.Gender;
import click.ahimmoyak.studentservice.auth.common.UserRole;
import click.ahimmoyak.studentservice.auth.dto.UserInformationRequestDto;
import click.ahimmoyak.studentservice.company.entity.Affiliation;
import click.ahimmoyak.studentservice.global.entity.Timestamped;
import click.ahimmoyak.studentservice.institution.entity.Manager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user")
    private Manager manager;

    @OneToOne(mappedBy = "user")
    private Affiliation affiliation;


    public void patch(UserInformationRequestDto requestDTO, String passwordEncoder) {
        if (passwordEncoder != null) {
            this.password = passwordEncoder;
        }
        if (requestDTO.getPhone() != null) {
            this.phone = requestDTO.getPhone();
        }
        if (requestDTO.getEmail() != null) {
            this.email = requestDTO.getEmail();
        }
    }
    public void patch() {
        this.role = UserRole.MANAGER;
    }

    public void updateRole(UserRole role) {
        if (role != null) {
            this.role = role;
        }
    }

}
