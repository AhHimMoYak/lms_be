package com.example.ahimmoyakbackend.course.entity;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents")
public class Contents extends Timestamped {

    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private ContentType type;

    @Column(nullable = false)
    private Integer idx;

    @Column(length = 255)
    private String s3Url;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

}