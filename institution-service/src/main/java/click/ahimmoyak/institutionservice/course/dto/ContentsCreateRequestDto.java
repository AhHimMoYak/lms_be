package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.ContentType;
import click.ahimmoyak.institutionservice.course.entity.Contents;
import click.ahimmoyak.institutionservice.course.entity.Curriculum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentsCreateRequestDto{

    private String title;
    private ContentType type;
    private MultipartFile file;

    public Contents toDto(Curriculum curriculum, int idx) {
        return Contents.builder()
                .title(this.title)
                .type(this.type)
                .curriculum(curriculum)
                .idx(idx)
                .build();
    }
}
