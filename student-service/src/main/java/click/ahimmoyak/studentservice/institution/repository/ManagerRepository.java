package click.ahimmoyak.studentservice.institution.repository;

import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.institution.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
