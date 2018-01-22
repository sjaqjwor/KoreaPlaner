package wooklee.koreaplaner.dtos;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private String name;
    private String password;
    private String email;
    private int sex;
    private String profileimage;
    private String birth;
    private String phonenumber;
    private String interest;

    public AddUserDto addUser(UserSignUp userSignUp){
        return AddUserDto.builder()
                .name(userSignUp.getName())
                .password(userSignUp.getPassword())
                .email(userSignUp.getEmail())
                .birth(userSignUp.getBirth())
                .phonenumber(userSignUp.getPhonenumber())
                .build();
    }

    public AddUserDto updateUser(FindUserDto findUserDto){
        return AddUserDto.builder()
                .email(findUserDto.getEmail())
                .interest(findUserDto.getInterest())
                .profileimage(findUserDto.getProfileimage())
                .build();
    }

}
