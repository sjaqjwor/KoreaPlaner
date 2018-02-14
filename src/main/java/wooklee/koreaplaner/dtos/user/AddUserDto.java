package wooklee.koreaplaner.dtos.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.security.Encriptor;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;

import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private int id;
    private String name;
    private String password;
    private String email;
    private int sex;
    private String profileimage;
    private String birth;
    private String phonenumber;
    private String interest;

    public static AddUserDto addUser(UserRequest userSignUp) throws NoSuchAlgorithmException{
        return AddUserDto.builder()
                .name(userSignUp.getName())
                .password(Encriptor.sha256(userSignUp.getPassword()))
                .email(userSignUp.getEmail())
                .birth(userSignUp.getBirth())
                .phonenumber(userSignUp.getPhonenumber())
                .build();
    }

    public static AddUserDto updateUser(FindUserDto findUserDto,UserRequest userRequest,String password){
        return AddUserDto.builder()
                .id(findUserDto.getId())
                .email(findUserDto.getEmail())
                .name(userRequest.getName())
                .interest(userRequest.getInterest())
                .password(password)
                .profileimage(findUserDto.getProfileimage())
                .phonenumber(findUserDto.getPhonenumber())
                .build();
    }

}
