package click.ahimmoyak.institutionservice.institution.controller;

import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDetailResponseDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvidesResponseDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.institution.service.InstitutionService;
import click.ahimmoyak.institutionservice.institution.dto.CourseProvideRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.GetInstitutionDetailRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.UpdateInstitutionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ) {
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

    @PatchMapping("/{institutionId}")
    public ResponseEntity<MessageResponseDto> updateInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateInstitutionRequestDto requestDto,
            @PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.updateInstitution(userDetails, requestDto, institutionId));
    }

    @GetMapping
    public ResponseEntity<CourseProvidesResponseDto> getCourseProvideListByInstitution(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(institutionService.getCourseProvideListByInstitution(userDetails));
    }

    @PatchMapping("/{courseProvideId}/response")
    public ResponseEntity<MessageResponseDto> courseProvideResponse(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable Long courseProvideId,
                                                                    @RequestBody CourseProvideRequestDto requestDto) {

        return ResponseEntity.ok(institutionService.courseProvideResponse(userDetails, courseProvideId, requestDto));
    }

    @GetMapping("/{courseProvideId}/courseProvideDetails")
    public ResponseEntity<CourseProvideDetailResponseDto> getCourseProvideDetailByInstitution(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long courseProvideId) {
        return ResponseEntity.ok(institutionService.getCourseProvideDetailByInstitution(userDetails, courseProvideId));
    }

    @PatchMapping("/{courseProvideId}/registrations")
    public ResponseEntity<MessageResponseDto> confirmEnrollments(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable Long courseProvideId) {

        return ResponseEntity.ok(institutionService.confirmEnrollments(userDetails, courseProvideId));
    }

}
