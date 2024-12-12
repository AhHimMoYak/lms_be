package click.ahimmoyak.institutionservice.institution.entity;

import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.global.entity.Timestamped;
import click.ahimmoyak.institutionservice.institution.dto.UpdateInstitutionRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institution")
public class Institution extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String ownerName;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String certifiedNumber;

    @Column
    private String email;

    @Column(length = 20)
    private String phone;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private String webSite;

    @Builder.Default
    @OneToMany(mappedBy = "institution")
    private List<Manager> managers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "institution")
    private List<CourseProvide> courseProvide = new ArrayList<>();

    public Institution patch(UpdateInstitutionRequestDto requestDTO) {
        if (requestDTO.InstitutionName() != null) {
            this.name = requestDTO.InstitutionName();
        }
        if (requestDTO.address() != null) {
            this.address = requestDTO.address();
        }
        if (requestDTO.email() != null) {
            this.email = requestDTO.email();
        }
        if (requestDTO.phone() != null) {
            this.phone = requestDTO.phone();
        }
        if (requestDTO.webSite() != null) {
            this.webSite = requestDTO.webSite();
        }
        if (requestDTO.description() != null) {
            this.description = requestDTO.description();
        }
        return this;
    }
}
