package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

//    Optional<Enrollment> findByUserAndCourse(User user, Course course);

//    Optional<Enrollment> findByUserIdAndCourseId(Long id, Long id1);

    //    Enrollment findByUser_UsernameAndCourse(String username, Course course);
    List<Enrollment> findAllByCourseProvide_Id(Long id);
}