package wooklee.koreaplaner.controllers.responses;

import lombok.*;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponse {
    private Object data;
    private Object user;
    private String msg;
    private StatusCode status;
}
