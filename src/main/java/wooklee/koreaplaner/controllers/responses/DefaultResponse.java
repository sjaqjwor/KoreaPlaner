package wooklee.koreaplaner.controllers.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DefaultResponse {

    private StatusCode status;
    private Object object;
}
