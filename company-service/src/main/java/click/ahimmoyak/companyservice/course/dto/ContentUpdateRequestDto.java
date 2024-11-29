package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentUpdateRequestDto {

    private String title;
    private ContentType type;
    private MultipartFile file;

}
