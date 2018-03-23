package wooklee.koreaplaner.controllers.responses;

import lombok.*;

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
