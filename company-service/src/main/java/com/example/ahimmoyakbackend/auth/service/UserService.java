package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    MessageResponseDto createUser(UserJoinRequestDto requestDto);

    MessageResponseDto login(UserLoginRequestDto requestDTO, HttpServletResponse response);

    MessageResponseDto reissue();

    MessageResponseDto checkExistName(ExistNameRequestDto requestDTO);

    MessageResponseDto checkVerification(UserVerificationRequestDto requestDTO, UserDetailsImpl userDetails);

    MessageResponseDto updatePersonalInformation(UserInformationRequestDto requestDTO, UserDetailsImpl userDetails);

    UserInformationResponseDto getPersonalInformation(String username);

    User getAuth(UserDetails userDetails);
}