package click.ahimmoyak.companyservice.course.service;

import click.ahimmoyak.companyservice.course.dto.CourseDetailResponseDto;
import click.ahimmoyak.companyservice.course.dto.CourseListResponseDto;
import click.ahimmoyak.companyservice.course.dto.EmployeeCourseListResponseDto;
import click.ahimmoyak.companyservice.course.common.CourseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
     CourseDetailResponseDto getDetail(long id);
     List<CourseListResponseDto> getAllList();
     Page<CourseListResponseDto> getAllList(Pageable pageable);
     List<CourseListResponseDto> getAllList(CourseCategory category);
     Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category);
     List<EmployeeCourseListResponseDto> getAllList(String userName);
}