package wooklee.koreaplaner.controllers.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DetailScheduleResponse {
    private Object data;
    private String msg;
    private StatusCode status;

    public DetailScheduleResponse(String msg, StatusCode status) {
        this.data = null;
        this.msg = msg;
        this.status = status;
    }
}
