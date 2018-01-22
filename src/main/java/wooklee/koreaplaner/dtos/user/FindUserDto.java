package wooklee.koreaplaner.dtos.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
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
