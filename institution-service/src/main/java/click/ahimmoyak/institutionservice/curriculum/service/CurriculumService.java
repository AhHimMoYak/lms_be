package click.ahimmoyak.institutionservice.curriculum.service;

import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateRequestDto;
import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateResponseDto;
import click.ahimmoyak.institutionservice.curriculum.dto.CurriculumListByInstitutionResponseDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CurriculumService {
    CurriculumCreateResponseDto add(long courseId, CurriculumCreateRequestDto requestDto);
    MessageResponseDto update(UserDetails userDetails, long curriculumId, String curriculumTitle);
    MessageResponseDto delete(UserDetails userDetails, long curriculumId);
    List<CurriculumListByInstitutionResponseDto> get(UserDetails userDetails, Long courseId);
}
