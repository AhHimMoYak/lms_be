package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.CourseCategory;
import lombok.Builder;

@Builder
public record CategoryResponseDto(
        String value,
        String title
) {
    public static CategoryResponseDto from(CourseCategory category) {
        return CategoryResponseDto.builder()
                .value(category.name())
                .title(category.getTitle())
                .build();
    }
}
