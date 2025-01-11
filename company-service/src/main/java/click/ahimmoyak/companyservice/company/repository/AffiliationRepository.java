package click.ahimmoyak.companyservice.company.repository;

import click.ahimmoyak.companyservice.company.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByUserId(Long userId);

    List<Affiliation> findByCompany_Id(Long companyId);

    int countByCompany_Id(Long id);
}
