package click.ahimmoyak.institutionservice.company.entity;

import click.ahimmoyak.institutionservice.auth.entity.User;
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
@Table(name = "affiliation")
public class Affiliation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}
