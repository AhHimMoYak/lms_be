package click.ahimmoyak.studentservice.auth.service;

import click.ahimmoyak.studentservice.auth.common.UserRole;
import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.auth.dto.*;
import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.auth.exception.InvalidPasswordException;
import click.ahimmoyak.studentservice.auth.jwt.JwtTokenProvider;
import click.ahimmoyak.studentservice.auth.repository.UserRepository;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import click.ahimmoyak.studentservice.global.exception.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public MessageResponseDto createUser(UserJoinRequestDto requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
//                .name(requestDto.getName())
//                .password(passwordEncoder.encode(requestDto.getPassword()))
//                .birth(requestDto.getBirth())
//                .email(requestDto.getEmail())
//                .gender(requestDto.getGender())
//                .role(UserRole.NORMAL)
                .build();

        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("회원가입 완료")
                .build();
    }

    public MessageResponseDto login(UserLoginRequestDto requestDTO, HttpServletResponse response) {
        User findUser = userRepository.findByUsername(requestDTO.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

//        JwsDto jwsDto = jwtTokenProvider.createAllToken(findUser.getUsername(), findUser.getEmail(), findUser.getRole());
//
//        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, jwsDto.getAccessToken());
//        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, jwsDto.getRefreshToken());

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

    public MessageResponseDto checkExistName(ExistNameRequestDto requestDTO) {
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

    public MessageResponseDto checkVerification(UserVerificationRequestDto requestDTO, UserDetailsImpl userDetails) {
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

    public MessageResponseDto updatePersonalInformation(UserInformationRequestDto requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

//        findUser.patch(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(findUser);

        return MessageResponseDto.builder()
                .message("변경하였습니다")
                .build();
    }

    public UserInformationResponseDto getPersonalInformation(String username) {
        User findUser = userRepository.findByUsername(username).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "사용자가 없습니다"));

        return UserInformationResponseDto.builder()
//                .name(findUser.getName())
//                .username(findUser.getUsername())
//                .birth(findUser.getBirth())
//                .phone(findUser.getPhone())
//                .email(findUser.getEmail())
//                .gender(findUser.getGender())
                .build();
    }

}