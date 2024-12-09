package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.common.CourseState;
import click.ahimmoyak.studentservice.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String instructor,
   long totalContentCount,
   long completedContentCount,
   CourseProvideState state,
   CourseCategory category
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getIntroduction())
                // Todo 코스리스트 반환시 CourseProvide 의 beginDate, endDate 함께 반환하도록 Dto 수정 (코스리스트가 수강중인 코스리스트일 경우는 코스프로바이드 정보를, 회사관리자가 계약을 위해 탐색할때 뜨는 코스리스트일경우는 코스 정보로 제공해야함)
//                .beginDate(course.getBeginDate())
//                .endDate(course.getEndDate())
                .category(course.getCategory())
                .build();
    }
}
