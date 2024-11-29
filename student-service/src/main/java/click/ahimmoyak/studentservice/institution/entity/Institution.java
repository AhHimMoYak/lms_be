package click.ahimmoyak.studentservice.institution.entity;

import click.ahimmoyak.studentservice.course.entity.CourseProvide;
import click.ahimmoyak.studentservice.global.entity.Timestamped;
import click.ahimmoyak.studentservice.institution.dto.UpdateInstitutionRequestDto;
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

    @Builder.Default
    @OneToMany(mappedBy = "institution")
    private List<Manager> managers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "institution")
    private List<CourseProvide> courseProvide = new ArrayList<>();

    public Institution patch(UpdateInstitutionRequestDto requestDTO) {
        if (requestDTO.ownerName() != null) {
            this.ownerName = requestDTO.ownerName();
        }
        if (requestDTO.email() != null) {
            this.email = requestDTO.email();
        }
        if (requestDTO.phone() != null) {
            this.phone = requestDTO.phone();
        }
        return this;
    }
}
