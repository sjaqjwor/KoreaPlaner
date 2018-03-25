package wooklee.koreaplaner.controllers.requests.schedule;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendRequest {
    List<String> uid;
}
