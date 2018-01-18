package wooklee.koreaplaner.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;

@Getter
@Setter
@ToString
public class AddUserDto {
    private String name;
    private String password;
    private String email;
    private int sex;
    private String profileimage;
    private String birth;
    private String phonenumber;
    private String interest;

    public AddUserDto(UserSignUp userSignUp){
        this.name=userSignUp.getName();
        this.password=userSignUp.getPassword();
        this.email=userSignUp.getEmail();
        this.birth=userSignUp.getBirth();
        this.phonenumber=userSignUp.getPhonenumber();
    }
}
