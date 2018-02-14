package wooklee.koreaplaner.dtos.user;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindUserDto {
    private int id;
    private String name;
    private String profileimage;
    private String email;
    private int sex;
    private String birth;
    private String phonenumber;
    private String interest;


}
