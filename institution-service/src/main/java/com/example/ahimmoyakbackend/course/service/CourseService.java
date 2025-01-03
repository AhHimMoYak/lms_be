package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CourseService {
     CourseDetailResponseDto getDetail(long id);
     Long create(UserDetails userDetails, CourseCreateRequestDto requestDto);
     boolean update(UserDetails userDetails, long id, CourseCreateRequestDto requestDto);
     boolean delete(UserDetails userDetails, long id);
     List<CourseListResponseDto> getListByInstitution(UserDetails userDetails);
     List<CourseListResponseDto> getAllList();
     Page<CourseListResponseDto> getAllList(Pageable pageable);
     List<CourseListResponseDto> getAllList(CourseCategory category);
     Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category);
     List<EmployeeCourseListResponseDto> getAllList(String userName);
     MessageResponseDto saveContents(Long curriculumId, List<GetContentsRequestDto> requestDtos);
}