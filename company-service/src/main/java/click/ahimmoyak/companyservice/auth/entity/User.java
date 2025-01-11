package click.ahimmoyak.companyservice.auth.entity;

import click.ahimmoyak.companyservice.auth.dto.UserInformationRequestDto;
import click.ahimmoyak.companyservice.company.entity.Affiliation;
import click.ahimmoyak.companyservice.institution.entity.Manager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @OneToOne(mappedBy = "user")
    private Manager manager;

    @OneToOne(mappedBy = "user")
    private Affiliation affiliation;


//    public void patch(UserInformationRequestDto requestDTO, String passwordEncoder) {
//        if (passwordEncoder != null) {
//            this.password = passwordEncoder;
//        }
////        if (requestDTO.getPhone() != null) {
////            this.phone = requestDTO.getPhone();
////        }
////        if (requestDTO.getEmail() != null) {
////            this.email = requestDTO.getEmail();
//    }
}
//    public void patch() {
//        this.role = UserRole.MANAGER;
//    }
//
//    public void updateRole(UserRole role) {
//        if (role != null) {
//            this.role = role;
//        }
//    }

//}
