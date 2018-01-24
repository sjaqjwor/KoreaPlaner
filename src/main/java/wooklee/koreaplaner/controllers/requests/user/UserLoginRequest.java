package wooklee.koreaplaner.controllers.requests.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {
    private String email;
    private String password;
}
