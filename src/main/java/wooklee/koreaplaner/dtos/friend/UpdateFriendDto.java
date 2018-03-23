package wooklee.koreaplaner.dtos.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateFriendDto {
    private Long uid;
    private Long fid;
    private Long status;
}
