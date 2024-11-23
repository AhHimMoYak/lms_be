package com.example.ahimmoyakbackend.auth.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<MessageResponseDto> join(@RequestBody @Valid UserJoinRequestDto requestDto) {
        MessageResponseDto created = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }


    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
        MessageResponseDto responseDto = userService.login(requestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @PostMapping("/reissue")
    public ResponseEntity<MessageResponseDto> checkRefreshToken() {
        MessageResponseDto responseDto = userService.reissue();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/exist/name")
    public ResponseEntity<MessageResponseDto> checkExistName(@RequestBody ExistNameRequestDto requestDto){
        MessageResponseDto responseDto = userService.checkExistName(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/user/verification")
    public ResponseEntity<MessageResponseDto> checkVerification(@RequestBody UserVerificationRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDto responseDto = userService.checkVerification(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/user/update")
    public ResponseEntity<MessageResponseDto> updatePersonalInformation(@RequestBody UserInformationRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDto responseDto = userService.updatePersonalInformation(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInformationResponseDto> checkUser(@RequestParam String username) {
        UserInformationResponseDto responseDto = userService.getPersonalInformation(username);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}