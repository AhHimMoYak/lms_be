package click.ahimmoyak.companyservice.company.controller;

import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.company.dto.*;
import click.ahimmoyak.companyservice.company.service.CompanyService;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/companies/info")
    public ResponseEntity<CompanyDetailResponseDto> getCompany(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(companyService.getCompany(userId));
    }

    @PatchMapping("/companies")
    public ResponseEntity<MessageResponseDto> updateCompany(
            @RequestParam Long companyId,
            @RequestBody UpdateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.updateCompany( companyId, requestDto));
    }

    // 내가 회사 탈퇴
    @DeleteMapping("/companies/affiliations")
    public ResponseEntity<MessageResponseDto> detachCompany(
            @RequestParam Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.disconnectCompany(userId));
    }

    // supervisor 가 해당 유저를 회사에서 탈퇴
    @DeleteMapping("/companies/employees")
    public ResponseEntity<MessageResponseDto> deleteAffiliation(
            @RequestParam String username
    ) {
        return ResponseEntity.ok(companyService.deleteAffiliation(username));
    }

    @GetMapping("/companies/employees")
    public ResponseEntity<List<GetEmployeeListResponseDto>> getEmployeeList(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(companyService.getEmployeeList(userId));
    }

    @PostMapping("/companies/courseProvides")
    public ResponseEntity<MessageResponseDto> CreateCourseProvide(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestBody CreateCourseProvideRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCourseProvider(userId,courseId,requestDto));
    }

    @GetMapping("/companies/courseProvides/list")
    public ResponseEntity<List<CourseProvideListResponseDto>> getCourseProvideList(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(companyService.getCourseProvideList(userId));
    }

    @PostMapping("/companies/courseProvides/employees")
    public ResponseEntity<MessageResponseDto> submitEmployeeListForEnrollment(
            @RequestParam Long userId,
            @RequestBody submitEmployeeListRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.submitEmployeeListForEnrollment(userId,requestDto));
    }

    @GetMapping("/companies/dashboard/info")
    public ResponseEntity<CompanyDashboardResponseDto> companyinfo(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(companyService.companyDashboard(userId));
    }

}
