package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String instructor,
   int period,
   CourseState state,
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
                .state(course.getState())
                .category(course.getCategory())
                .build();
    }
}
