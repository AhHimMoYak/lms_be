package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_board")
public class CourseBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "courseBoard")
    private List<CourseComment> commits;

    public int getCommitCount(){
        return commits != null ? commits.size() : 0;
    }
}