package click.ahimmoyak.studentservice.course.dto;

public record CourseIdDto(
        Long courseId
) {
    public static CourseIdDto valueOf(Long courseId) {
        return new CourseIdDto(courseId);
    }
}
