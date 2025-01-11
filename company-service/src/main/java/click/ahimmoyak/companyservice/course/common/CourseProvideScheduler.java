package click.ahimmoyak.companyservice.course.common;

import click.ahimmoyak.companyservice.course.entity.CourseProvide;
import click.ahimmoyak.companyservice.course.repository.CourseProvideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseProvideScheduler {

    private final CourseProvideRepository courseProvideRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateCourseProvideState() {
        LocalDate today = LocalDate.now();


        List<CourseProvide> startingCourses = courseProvideRepository.findAllByBeginDateAndState(today, CourseProvideState.NOT_STARTED);
        startingCourses.forEach(CourseProvide::startCourse);

        List<CourseProvide> endingCourses = courseProvideRepository.findAllByEndDateAndState(today, CourseProvideState.ONGOING);
        endingCourses.forEach(CourseProvide::finishCourse);

        courseProvideRepository.saveAll(startingCourses);
        courseProvideRepository.saveAll(endingCourses);

    }

}
