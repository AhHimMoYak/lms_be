package click.ahimmoyak.companyservice.auth.service;

import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.auth.dto.*;
import click.ahimmoyak.companyservice.auth.entity.User;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;
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