package click.ahimmoyak.companyservice.institution.repository;

import click.ahimmoyak.companyservice.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    boolean existsByBusinessNumber(String businessNumber);

    boolean existsByCertifiedNumber(String certifiedNumber);

}