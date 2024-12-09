package click.ahimmoyak.institutionservice.curriculum.controller;


import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateRequestDto;
import click.ahimmoyak.institutionservice.course.dto.CurriculumCreateResponseDto;
import click.ahimmoyak.institutionservice.curriculum.dto.CurriculumListByInstitutionResponseDto;
import click.ahimmoyak.institutionservice.curriculum.service.CurriculumService;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/institutions/courses/{courseId}")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @GetMapping("/curriculums")
    public ResponseEntity<List<CurriculumListByInstitutionResponseDto>> getCurriculumListByInstitution(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(curriculumService.get(userDetails, courseId));
    }

    @PostMapping("/curriculums")
    public ResponseEntity<CurriculumCreateResponseDto> addCurriculum(
            @PathVariable("courseId") long courseId,
            @RequestBody CurriculumCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(curriculumService.add(courseId, requestDto));
    }

    @PatchMapping("/curriculums/{curriculumId}")
    public ResponseEntity<MessageResponseDto> updateCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @RequestBody CurriculumCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(curriculumService.update(userDetails, curriculumId, requestDto.title()));
    }

    @DeleteMapping("/curriculums/{curriculumId}")
    public ResponseEntity<MessageResponseDto> deleteCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId
    ) {
        return ResponseEntity.ok(curriculumService.delete(userDetails, curriculumId));
    }
}
