package click.ahimmoyak.institutionservice.live.service;

import click.ahimmoyak.institutionservice.live.dto.LiveQuizCreateRequestDto;
import click.ahimmoyak.institutionservice.live.dto.LiveQuizResponseDto;
import click.ahimmoyak.institutionservice.live.dto.QuizAnswerMsgDto;
import click.ahimmoyak.institutionservice.live.dto.QuizStatusSubDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface LiveQuizService {
    public boolean create(UserDetails userDetails, long liveId, LiveQuizCreateRequestDto requestDto);
    public List<LiveQuizResponseDto> getList(long liveId);
    public LiveQuizResponseDto send(long liveId, long quizId);
    public List<QuizStatusSubDto> answer(long liveId, QuizAnswerMsgDto pubDto);
    public boolean saveDataToDb(long liveId);
}
