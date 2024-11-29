package click.ahimmoyak.studentservice.course.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CourseProvidesResponseDto(
        List<CourseProvideDto> courseDetailResponseDtoList
) {
    public static CourseProvidesResponseDto from(List<CourseProvideDto> courseProvideDtoList) {
        return CourseProvidesResponseDto.builder()
                .courseDetailResponseDtoList(courseProvideDtoList)
                .build();
    }
}
