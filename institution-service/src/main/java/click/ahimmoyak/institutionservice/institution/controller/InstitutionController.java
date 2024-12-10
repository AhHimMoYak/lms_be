package click.ahimmoyak.institutionservice.institution.controller;

import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDetailResponseDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvidesResponseDto;
import click.ahimmoyak.institutionservice.course.dto.EnrollmentRequestDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.institution.dto.*;
import click.ahimmoyak.institutionservice.institution.service.InstitutionService;
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
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(@RequestParam Long userId
    ){
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userId));
    }

    @GetMapping("/{institutionId}/details")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails, institutionId));
    }


    @PatchMapping
    public ResponseEntity<MessageResponseDto> updateInstitution(
            @RequestParam Long userId,
            @RequestBody UpdateInstitutionRequestDto requestDto) {
        return ResponseEntity.ok(institutionService.updateInstitution(userId, requestDto));
    }

    @GetMapping
    public ResponseEntity<CourseProvidesResponseDto> getCourseProvideListByInstitution(@RequestParam Long userId) {

        return ResponseEntity.ok(institutionService.getCourseProvideListByInstitution(userId));
    }

    @GetMapping("/{courseProvideId}/startCourseProvideDetails")
    public ResponseEntity<StartCourseProvideDetailResponseDto> getStartCourseProvideDetailByInstitution(@RequestParam Long userId, @PathVariable Long courseProvideId) {

        return ResponseEntity.ok(institutionService.getStartCourseProvideDetailByInstitution(userId, courseProvideId));
    }

    @PatchMapping("/{courseProvideId}/response")
    public ResponseEntity<MessageResponseDto> courseProvideResponse(@RequestParam Long userId,
                                                                    @PathVariable Long courseProvideId,
                                                                    @RequestBody CourseProvideRequestDto requestDto) {

        return ResponseEntity.ok(institutionService.courseProvideResponse(userId, courseProvideId, requestDto));
    }

    @GetMapping("/{courseProvideId}/courseProvideDetails")
    public ResponseEntity<CourseProvideDetailResponseDto> getCourseProvideDetailByInstitution(@RequestParam Long userId, @PathVariable Long courseProvideId) {
        return ResponseEntity.ok(institutionService.getCourseProvideDetailByInstitution(userId, courseProvideId));
    }

    @PatchMapping("/{courseProvideId}/registrations")
    public ResponseEntity<MessageResponseDto> confirmEnrollments(@RequestParam Long userId,
                                                                 @PathVariable Long courseProvideId,
                                                                 @RequestBody EnrollmentRequestDto requestDto) {

        return ResponseEntity.ok(institutionService.confirmEnrollments(userId, courseProvideId, requestDto));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<GetDashboardResponseDto> getDashboard(@RequestParam Long userId){
        return ResponseEntity.ok(institutionService.getDashboard(userId));
    }



}
