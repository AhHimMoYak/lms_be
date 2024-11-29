package click.ahimmoyak.institutionservice.live.entity;

import click.ahimmoyak.institutionservice.live.common.LiveState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "live")
public class LiveStatus {
    @Id
    private long id;
    private LiveState status;
}
