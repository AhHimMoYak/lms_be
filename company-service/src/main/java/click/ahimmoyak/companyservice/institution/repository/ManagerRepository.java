package click.ahimmoyak.companyservice.institution.repository;

import click.ahimmoyak.companyservice.auth.entity.User;
import click.ahimmoyak.companyservice.institution.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query("select m from Manager m where m.user = ?1")
    Manager findByUser(User user);


}
