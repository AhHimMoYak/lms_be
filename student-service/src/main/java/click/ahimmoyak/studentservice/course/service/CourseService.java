package click.ahimmoyak.studentservice.course.service;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.course.dto.*;
import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CourseService {
     CourseDetailResponseDto getDetail(long id);
     CourseDetailResponseDto getDetail(long id, UserDetails userDetails);
     List<CourseListResponseDto> getAllList();
     Page<CourseListResponseDto> getAllList(Pageable pageable);
     List<CourseListResponseDto> getAllList(CourseCategory category);
     Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category);
     List<EmployeeCourseListResponseDto> getAllList(String userName);
     ContentDetailResponseDto getContentDetail(String contentId);
     MessageResponseDto createContentHistory(UserDetailsImpl userDetails, String contentId);
     List<CourseListResponseDto> getCourseList(UserDetailsImpl userDetails);
     List<ContentsHistoryResponseDto> getContentHistory(UserDetailsImpl userDetails,Long courseProvideId);
}