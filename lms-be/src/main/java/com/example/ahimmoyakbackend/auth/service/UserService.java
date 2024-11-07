package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    MessageResponseDto createUser(UserJoinRequestDTO requestDto);

    MessageResponseDto login(UserLoginRequestDTO requestDTO, HttpServletResponse response);

    UserReissueResponseDTO reissue();

    ExistNameResponseDTO checkExistName(ExistNameRequestDTO requestDTO);

    UserVerificationResponseDTO checkVerification(UserVerificationRequestDTO requestDTO, UserDetailsImpl userDetails);

    UserInformationResponseDto updatePersonalInformation(UserInformationRequestDTO requestDTO, UserDetailsImpl userDetails);

    UserInformationResponseDto getPersonalInformation(UserIdentificationRequestDto requestDto);

    MessageResponseDto disconnectCompany(FormerCompanyInfoRequestDto requestDto, UserDetailsImpl userDetails);

    User getAuth(UserDetails userDetails);
}