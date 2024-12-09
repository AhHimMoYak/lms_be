package click.ahimmoyak.institutionservice.course.service;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.dto.*;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CourseService {
     CourseDetailResponseDto getDetail(long id);
     CourseCreateResponseDto create(Long userId, CourseCreateRequestDto requestDto);
     boolean update(long id, CourseUpdateRequestDto requestDto);
     boolean delete(UserDetails userDetails, long id);
     List<CourseListResponseDto> getListByInstitution(Long userId);
     List<CourseListResponseDto> getAllList();
     Page<CourseListResponseDto> getAllList(Pageable pageable);
     List<CourseListResponseDto> getAllList(CourseCategory category);
     Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category);
     List<EmployeeCourseListResponseDto> getAllList(String userName);
     MessageResponseDto saveContents(Long curriculumId, List<GetContentsRequestDto> requestDtos);
}