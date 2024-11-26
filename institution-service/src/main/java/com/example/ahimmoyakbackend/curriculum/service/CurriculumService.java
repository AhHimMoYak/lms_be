package com.example.ahimmoyakbackend.curriculum.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CurriculumService {
    boolean add(UserDetails userDetails, long courseId, String curriculumTitle);
    boolean update(UserDetails userDetails, long curriculumId, String curriculumTitle);
    boolean delete(UserDetails userDetails, long curriculumId);
}
