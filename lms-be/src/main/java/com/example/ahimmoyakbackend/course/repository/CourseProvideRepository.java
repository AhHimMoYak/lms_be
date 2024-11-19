package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseProvideRepository extends JpaRepository<CourseProvide, Long> {

    List<CourseProvide> findAllByInstitution(Institution institution);

    List<CourseProvide> findAllByBeginDateAndState(LocalDate beginDate, CourseProvideState state);

    List<CourseProvide> findAllByEndDateAndState(LocalDate endDate, CourseProvideState state);
}

