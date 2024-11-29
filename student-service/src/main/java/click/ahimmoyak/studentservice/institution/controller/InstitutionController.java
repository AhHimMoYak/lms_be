package click.ahimmoyak.studentservice.institution.controller;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import click.ahimmoyak.studentservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.studentservice.institution.dto.GetInstitutionDetailRequestDto;
import click.ahimmoyak.studentservice.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ) {
        // test
        return ResponseEntity.ok(institutionService.createInstitution(userDetails, requestDto));
    }

    @GetMapping("/details")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails));
    }

    @GetMapping("/{institutionId}/details")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails, institutionId));
    }

}
