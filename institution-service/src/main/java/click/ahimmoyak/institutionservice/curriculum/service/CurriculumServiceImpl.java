package click.ahimmoyak.institutionservice.curriculum.service;

import click.ahimmoyak.institutionservice.auth.service.UserService;
import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateRequestDto;
import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateResponseDto;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.Curriculum;
import click.ahimmoyak.institutionservice.course.repository.CourseRepository;
import click.ahimmoyak.institutionservice.course.repository.CurriculumRepository;
import click.ahimmoyak.institutionservice.curriculum.dto.CurriculumListByInstitutionResponseDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
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