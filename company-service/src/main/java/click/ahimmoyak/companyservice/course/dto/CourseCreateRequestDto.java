package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.common.CourseState;
import click.ahimmoyak.companyservice.course.entity.Course;
import click.ahimmoyak.companyservice.institution.entity.Institution;
import lombok.Builder;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction,
   String instructor,
   Institution institution,
   CourseCategory category
) {
    // Todo 코스 생성시 교육기관 정보가 들어가도록 수정해야함
    public Course toEntity() {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .category(this.category)
                .institution(this.institution)
                .state(CourseState.AVAILABLE)
                .instructor(this.instructor)
                .build();
    }
}
