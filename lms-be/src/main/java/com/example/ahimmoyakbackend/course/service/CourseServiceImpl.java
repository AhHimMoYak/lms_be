package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final CourseRepository courseRepository;

    @Override
    public CourseDetailResponseDto getDetail(long id) {
        return courseRepository.findById(id)
                .map(course -> CourseDetailResponseDto
                        .from(course, course.getCurriculumList().stream()
                                .map(curriculum -> CurriculumListResponseDto
                                        .from(curriculum, curriculum.getContentsList().stream()
                                                .map(ContentListResponseDto::from
                                                ).toList())
                                ).toList())).orElse(null);
    }

    @Override
    @Transactional
    // Todo 코스 생성시 교육기관 정보도 들어가도록 수정필요
    public Long create(UserDetails userDetails, CourseCreateRequestDto requestDto) {
        return courseRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public boolean update(UserDetails userDetails, long id, CourseCreateRequestDto requestDto) {
        Course course = courseRepository.findById(id).orElse(null);
        // Todo 코스 업데이트시 교육기관 매니저만 수정가능하도록 권한 확인 해야함 (아래 주석 참고)
//        if (course == null || !course.getTutor().equals(userService.getAuth(userDetails))) {
//            return false;
//        }
        courseRepository.save(course.patch(requestDto));
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UserDetails userDetails, long id) {
        Course course = courseRepository.findById(id).orElse(null);
        // Todo 코스 삭제시 교육기관 매니저만 삭제가능하도록 권한 확인 해야함 (아래 주석 참고)
//        if (course == null || !course.getTutor().equals(userService.getAuth(userDetails))) {
//            return false;
//        }
        courseRepository.save(course.setState(CourseState.REMOVED));
        return true;
    }

    @Override
    public List<CourseListResponseDto> getList(UserDetails userDetails) {
        User user = userService.getAuth(userDetails);
        List<Course> courseList;
        // Todo 코스 리스트 받아올 때 강사의 리스트가 아닌, 매니저에 대해 해당 교육기관의 모든 코스 찾아오도록 수정해야함
        if ( true
//                user.isTutorState()
        ){
            courseList = courseRepository.findAllByTutor(user);
        }else {
            courseList = courseRepository.findAllByEnrollments_User(user);
        }
        return courseList.stream()
                .map(CourseListResponseDto::from)
                .toList();
    }

    @Override
    public List<CourseListResponseDto> getAllList() {
        return courseRepository.findAllOrderByState().stream()
                .map(CourseListResponseDto::from)
                .toList();
    }

    @Override
    public Page<CourseListResponseDto> getAllList(Pageable pageable) {
        return courseRepository.findAllOrderByState(pageable)
                .map(CourseListResponseDto::from);
    }

    @Override
    public List<CourseListResponseDto> getAllList(CourseCategory category) {
        return courseRepository.findAllByCategoryOrderByState(category).stream()
                .map(CourseListResponseDto::from)
                .toList();
    }

    @Override
    public Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category) {
        return courseRepository.findAllByCategoryOrderByState(category, pageable)
                .map(CourseListResponseDto::from);
    }
}
