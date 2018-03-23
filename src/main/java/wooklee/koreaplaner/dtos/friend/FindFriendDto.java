package wooklee.koreaplaner.dtos.friend;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindFriendDto {
    private int id;
    private String profileimage;
    private String sex;
    private String name;
    private String email;
}
