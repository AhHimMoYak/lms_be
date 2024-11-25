package com.example.ahimmoyakbackend.course.common;

public enum CourseProvideState {
    PENDING, DECLINED, ACCEPTED, ATTENDEE_PENDING, NOT_STARTED, ONGOING, FINISHED, REMOVED;
}

/**
 * PENDING : courseProvide 생성 후 수락/거절 까지 보류 상태
 * DECLINED : 거절
 * ACCEPTED : PENDING -> ACCEPTED 요청 수락
 * ATTENDEE_PENDING : 인원 선택완료 까지 보류 상태
 * NOT_STARTED : 인원 선택이 완료 되고 COURSE가 시작되기 전
 * ONGOING : 교육과정이 진행중
 * FINISHED : 교육과정 종료
 * REMOVED : 교육과정 삭제
 */