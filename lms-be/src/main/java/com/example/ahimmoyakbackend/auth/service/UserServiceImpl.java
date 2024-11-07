package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.exception.InvalidPasswordException;
import com.example.ahimmoyakbackend.auth.jwt.JwtTokenProvider;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MessageResponseDto createUser(UserJoinRequestDTO requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
                .role(UserRole.NORMAL)
                .build();

        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("회원가입 완료")
                .build();
    }

    public MessageResponseDto login(UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        User findUser = userRepository.findByUsername(requestDTO.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        JwsDTO jwsDto = jwtTokenProvider.createAllToken(findUser.getUsername(), findUser.getEmail(), findUser.getRole());

        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, jwsDto.getAccessToken());
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, jwsDto.getRefreshToken());

        return MessageResponseDto.builder()
                .message("로그인 완료")
                .build();
    }

    public User getAuth(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("인증되지 않은 사용자입니다.")
        );

    }

    public MessageResponseDto reissue() {

        return MessageResponseDto.builder()
                .message("refresh token reissue complete")
                .build();
    }

    public MessageResponseDto checkExistName(ExistNameRequestDTO requestDTO) {
        try {
            if(!userRepository.existsByUsername(requestDTO.getUsername())) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
        } catch (Exception e) {
            return MessageResponseDto.builder()
                    .message("true")
                    .build();
        }

        return MessageResponseDto.builder()
                .message("false")
                .build();
    }

    public MessageResponseDto checkVerification(UserVerificationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException("false");
        }
        return MessageResponseDto
                .builder()
                .message("true")
                .build();
    }

    public MessageResponseDto updatePersonalInformation(UserInformationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        findUser.patch(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(findUser);

        return MessageResponseDto.builder()
                .message("변경하였습니다")
                .build();
    }

    @Override
    public UserInformationResponseDto getPersonalInformation(UserIdentificationRequestDto requestDto) {
        return null;
    }

    @Override
    public MessageResponseDto disconnectCompany(FormerCompanyInfoRequestDto requestDto, UserDetailsImpl userDetails) {
        return null;
    }

}