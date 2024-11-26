package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    long countByCurriculum(Curriculum curriculum);

    Optional<Contents> findById(String contentId);

    List<Contents> findAllByCurriculumId(Long curriculumId);
}