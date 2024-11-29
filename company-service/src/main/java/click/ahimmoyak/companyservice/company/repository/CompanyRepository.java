package click.ahimmoyak.companyservice.company.repository;

import click.ahimmoyak.companyservice.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByBusinessNumber(String businessNumber);

    List<Company> findByNameContaining(String name);

    Optional<Company> findByName(String name);

}
