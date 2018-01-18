package wooklee.koreaplaner.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FindUserDto {
    private String name;
    private String profileimage;
    private String email;
    private int sex;
    private String birth;
    private String phonenumber;
    private String interest;
}
