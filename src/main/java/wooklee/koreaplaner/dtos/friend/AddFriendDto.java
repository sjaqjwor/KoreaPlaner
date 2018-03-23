package wooklee.koreaplaner.dtos.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddFriendDto {
    private Long uid;
    private Long fid;
}
