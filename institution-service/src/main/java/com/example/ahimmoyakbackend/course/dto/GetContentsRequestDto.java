package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import lombok.Builder;

@Builder
public record GetContentsRequestDto(
        String contentId,
        String contentTitle,
        Integer idx,
        ContentType contentType,
        String s3Url
) {
}
