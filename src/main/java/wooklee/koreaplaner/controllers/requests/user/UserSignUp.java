package wooklee.koreaplaner.controllers.requests.user;

import lombok.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUp {
    private String name;
    private String password;
    private String email;
    private String birth;
    private String phonenumber;
}
