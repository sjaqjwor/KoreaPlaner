package wooklee.koreaplaner.controllers.requests.friend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AcceptFriendRequest {
    private Long uid;
    private Long fid;
    private Long status;
}
