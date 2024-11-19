package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.CourseProvide;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseProvideRepository extends JpaRepository<CourseProvide, Long> {
    List<CourseProvide> findByCompany_Id(Long id);

    List<CourseProvide> findAllByInstitution(Institution institution);
}
