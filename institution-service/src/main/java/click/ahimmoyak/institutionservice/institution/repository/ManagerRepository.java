package click.ahimmoyak.institutionservice.institution.repository;

import click.ahimmoyak.institutionservice.auth.entity.User;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import click.ahimmoyak.institutionservice.institution.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
