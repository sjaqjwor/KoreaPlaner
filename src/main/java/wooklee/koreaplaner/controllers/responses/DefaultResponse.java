package wooklee.koreaplaner.controllers.responses;

import lombok.*;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DefaultResponse {

    private StatusCode status;
    private Object object;
}
