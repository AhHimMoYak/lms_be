package click.ahimmoyak.studentservice.company.service;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.company.dto.CheckCompanyResponseDto;
import click.ahimmoyak.studentservice.company.dto.CompanyDetailResponseDto;
import click.ahimmoyak.studentservice.company.dto.CreateCompanyRequestDto;
import click.ahimmoyak.studentservice.company.dto.SearchCompanyResponseDto;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);

    List<SearchCompanyResponseDto> searchCompany(String name);

    CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails);

    CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail);

    MessageResponseDto addAffiliation(UserDetailsImpl userDetails, String companyName);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);


}
