package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.entity.Curriculum;
import lombok.Builder;

import java.util.List;

@Builder
public record CurriculumListResponseDto(
    long id,
    String title,
    List<ContentListResponseDto> contentList
) {
    public static CurriculumListResponseDto from(Curriculum curriculum, List<ContentListResponseDto> contentList) {
        return CurriculumListResponseDto.builder()
                .id(curriculum.getId())
                .title(curriculum.getTitle())
                .contentList(contentList)
                .build();
    }
}
