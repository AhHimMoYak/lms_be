package com.example.ahimmoyakbackend.curriculum.service;

import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateResponseDto;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumListByInstitutionResponseDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;

    @Override
    @Transactional
    public CurriculumCreateResponseDto add(UserDetails userDetails, long courseId, CurriculumCreateRequestDto requestDto) {
        Course course = courseRepository.findById(courseId).orElse(null);
        long count = curriculumRepository.countByCourse(course);

        return CurriculumCreateResponseDto.builder().curriculumId( curriculumRepository.save(Curriculum.builder()
                .title(requestDto.title())
                .idx((int)(count+1))
                .course(course)
                .build()).getId()).build();
    }

    @Override
    @Transactional
    public MessageResponseDto update(UserDetails userDetails, long curriculumId, String curriculumTitle) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        assert curriculum != null;
        curriculumRepository.save(curriculum.patch(curriculumTitle));
        return MessageResponseDto.builder().message("커리큘럼 수정 완료").build();

    }

    @Override
    @Transactional
    public MessageResponseDto delete(UserDetails userDetails, long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        assert curriculum != null;
        curriculumRepository.delete(curriculum);
        return MessageResponseDto.builder().message("커리큘럼 삭제 완료").build();

    }

    @Override
    public List<CurriculumListByInstitutionResponseDto> get(UserDetails userDetails, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 존재하지않습니다."));

        return course.getCurriculumList().stream()
                .map(curriculum -> CurriculumListByInstitutionResponseDto.builder()
                        .curriculumId(curriculum.getId())
                        .title(curriculum.getTitle())
                        .idx(curriculum.getIdx())
                        .build())
                .toList();
    }
}