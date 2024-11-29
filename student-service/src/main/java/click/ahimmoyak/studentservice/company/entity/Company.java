package click.ahimmoyak.studentservice.company.entity;

import click.ahimmoyak.studentservice.company.dto.UpdateCompanyRequestDto;
import click.ahimmoyak.studentservice.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "owner_name", length = 255)
    private String ownerName;

    @Column(name = "business_number", nullable = false, length = 255)
    private String businessNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    public void patch(UpdateCompanyRequestDto requestDto) {
        if (!Objects.equals(this.name,requestDto.name()))
            this.name = requestDto.name();
        if (!Objects.equals(this.ownerName, requestDto.ownerName()))
            this.ownerName = requestDto.ownerName();
        if (!Objects.equals(this.email, requestDto.email()))
            this.email = requestDto.email();
        if (!Objects.equals(this.phone, requestDto.phone()))
            this.phone = requestDto.phone();
    }

}
