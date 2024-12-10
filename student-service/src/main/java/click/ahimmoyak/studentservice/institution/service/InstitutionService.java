package click.ahimmoyak.studentservice.institution.service;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import click.ahimmoyak.studentservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.studentservice.institution.dto.GetInstitutionDetailRequestDto;

public interface InstitutionService {
    MessageResponseDto createInstitution(Long userId, CreateInstitutionRequestDto requestDto);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId);

}
