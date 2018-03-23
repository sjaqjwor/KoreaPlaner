package wooklee.koreaplaner.controllers.requests.friend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddFriendRequest {
    private Long uidx;
    private Long fidx;
}
