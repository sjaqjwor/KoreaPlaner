package wooklee.koreaplaner.controllers.requests.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.beans.Transient;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String phonenumber;
    private String password;
    private String interest;
}
