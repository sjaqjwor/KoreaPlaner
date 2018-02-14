package wooklee.koreaplaner.controllers.requests.user;

import lombok.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String phonenumber;

    private String birth;
    private String interest;
}
