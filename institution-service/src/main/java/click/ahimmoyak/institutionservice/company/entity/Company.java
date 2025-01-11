package click.ahimmoyak.institutionservice.company.entity;

import click.ahimmoyak.institutionservice.global.entity.Timestamped;
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


}
