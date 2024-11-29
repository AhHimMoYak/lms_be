package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.live.entity.AttendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendHistoryRepository extends JpaRepository<AttendHistory, Long> {
    List<AttendHistory> findByLiveStreaming_IdAndAttendance(Long id, Boolean attendance);

    AttendHistory findByLiveStreaming_IdAndEnrollment_User_Username(Long id, String username);
}