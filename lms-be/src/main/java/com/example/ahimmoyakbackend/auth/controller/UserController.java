package com.example.ahimmoyakbackend.auth.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<MessageResponseDto> join(@RequestBody @Valid UserJoinRequestDTO requestDto) {
        MessageResponseDto created = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }


    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(@RequestBody UserLoginRequestDTO requestDto, HttpServletResponse response) {
        MessageResponseDto responseDto = userService.login(requestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @PostMapping("/reissue")
    public ResponseEntity<UserReissueResponseDTO> checkRefreshToken() {
        UserReissueResponseDTO responseDto = userService.reissue();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/exist/name")
    public ResponseEntity<ExistNameResponseDTO> checkExistName(@RequestBody ExistNameRequestDTO requestDTO){
        ExistNameResponseDTO responseDTO = userService.checkExistName(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user/verification")
    public ResponseEntity<UserVerificationResponseDTO> checkVerification(@RequestBody UserVerificationRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserVerificationResponseDTO responseDTO = userService.checkVerification(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/user/update")
    public ResponseEntity<UserInformationResponseDto> updatePersonalInformation(@RequestBody UserInformationRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInformationResponseDto responseDTO = userService.updatePersonalInformation(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping()
    public ResponseEntity<UserInformationResponseDto> checkUser(@RequestBody UserIdentificationRequestDto requestDto){
        UserInformationResponseDto responseDto = userService.getPersonalInformation(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDto> disconnectCompany(@RequestBody FormerCompanyInfoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MessageResponseDto responseDto = userService.disconnectCompany(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}