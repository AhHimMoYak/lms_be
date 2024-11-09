package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final UserService userService;
    private final CompanyRepository companyRepository;
    private final AffiliationRepository affiliationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);

        if (companyRepository.existsByBusinessNumber(requestDto.business_number())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 존재하는 사업자 번호입니다.");
        }

        Company company = companyRepository.save(requestDto.toEntity());

        Affiliation affiliation = Affiliation.builder()
                .company(company)
                .user(user)
                .isSupervisor(true)
                .build();
        affiliationRepository.save(affiliation);

        user.updateRole(UserRole.SUPERVISOR);
        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("회사생성 완료")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchCompanyResponseDto> searchCompany(String name) {
        List<Company> companies = companyRepository.findByNameContaining(name);

        if (companies.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "해당하는 회사를 찾을 수 없습니다.");
        }

        return companies.stream()
                .map(SearchCompanyResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        if ( user.getRole() != UserRole.SUPERVISOR) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "회사를 수정할 권한이 없습니다.");
        }
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당 회사를 찾을 수 없습니다."));
        Company usercompany = user.getAffiliation().getCompany();
        if (company != usercompany) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 회사의 SUPERVISOR 가 아닙니다.");
        }
        company.patch(requestDto);
        companyRepository.save(company);

        return MessageResponseDto.builder()
                .message("회사 수정 완료")
                .build();
    }

    @Override
    public MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId) {
        return null;
    }

    @Override
    public MessageResponseDto checkCompanyEmail(CheckCompanyEmailRequestDto requestDto) {
        return null;
    }

    @Override
    public MessageResponseDto addAffiliation(AddAffiliationRequestDto requestDto) {
        return null;
    }

    @Override
    public GetEmployeeListResponseDto getEmployeeList(UserDetailsImpl userDetails) {
        return null;
    }

}
