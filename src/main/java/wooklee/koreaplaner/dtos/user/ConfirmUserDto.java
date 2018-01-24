package wooklee.koreaplaner.dtos.user;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmUserDto {
    private String email;
    private String password;

    public ConfirmUserDto confirmUserDto(UserLoginRequest userLoginRequest){
        return builder()
                .email(userLoginRequest.getEmail())
                .password(userLoginRequest.getPassword())
                .build();
    }
}
