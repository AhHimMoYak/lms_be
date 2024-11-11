package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UpdateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import com.example.ahimmoyakbackend.institution.dto.GetInstitutionDetailRequestDto;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.repository.InstitutionRepository;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final ManagerRepository managerRepository;

    @Override
    public MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {

        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지않는 user 입니다"));

        if (!user.getRole().equals(UserRole.MANAGER)) {
            throw new IllegalArgumentException("해당 사용자는 등록 권한이 없습니다");
        }

        if (institutionRepository.existsByBusinessNumber(requestDto.businessNumber())) {
            throw new IllegalArgumentException("이미 존재하는 법인번호 입니다");
        }

        if (institutionRepository.existsByCertifiedNumber(requestDto.certifiedNumber())) {
            throw new IllegalArgumentException("이미 존재하는 관리번호 입니다");
        }

        Institution institution = Institution.builder()
                .name(requestDto.name())
                .ownerName(requestDto.ownerName())
                .businessNumber(requestDto.businessNumber())
                .certifiedNumber(requestDto.certifiedNumber())
                .email(requestDto.email())
                .phone(requestDto.phone())
                .build();
        institutionRepository.save(institution);

        Manager manager = Manager.builder()
                .user(user)
                .institution(institution)
                .build();
        managerRepository.save(manager);

        return MessageResponseDto.builder().message("회사 생성 성공").build();
    }

    @Override
    public MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId) {

        Manager manager = managerRepository.findByUser(userDetails.getUser());
        if (!userDetails.getUser().getRole().equals(UserRole.MANAGER)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교육기관입니다."));
        institution.patch(requestDto);

        return MessageResponseDto.builder().message("회사 수정 성공").build();
    }

    @Override
    public GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId 입니다"));

        Institution institution = institutionRepository.findById(institutionId).orElseThrow(()-> new IllegalArgumentException("존재하지 않은 교육기관입니다."));

        return GetInstitutionDetailRequestDto.builder()
                .id(institution.getId())
                .institutionName(institution.getName())
                .ownerName(institution.getOwnerName())
                .businessNumber(institution.getBusinessNumber())
                .certifiedNumber(institution.getCertifiedNumber())
                .email(institution.getEmail())
                .phone(institution.getPhone())
                .build();
    }

    @Override
    public UserInstitutionIdResponseDto getInstitutionId(UserDetailsImpl userDetails) {
        Long institutionId = userDetails.getUser().getManager().getInstitution().getId();

        return UserInstitutionIdResponseDto.builder()
                .institutionId(institutionId)
                .build();
    }
}
