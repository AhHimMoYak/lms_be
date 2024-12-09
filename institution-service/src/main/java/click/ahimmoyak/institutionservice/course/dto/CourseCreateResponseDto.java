package click.ahimmoyak.institutionservice.course.dto;

import lombok.Builder;

@Builder
public record CourseCreateResponseDto(
        Long courseId
) {
    public static CourseCreateResponseDto from(long courseId) {
        return CourseCreateResponseDto.builder().courseId(courseId).build();
    }
}
