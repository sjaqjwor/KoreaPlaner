package wooklee.koreaplaner.controllers.requests.user;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
