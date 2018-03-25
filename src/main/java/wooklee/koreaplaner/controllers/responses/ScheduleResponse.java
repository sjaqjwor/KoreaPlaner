package wooklee.koreaplaner.controllers.responses;

import lombok.*;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ScheduleResponse {
    private Object data;
    private String msg;
    private StatusCode status;

    public ScheduleResponse(String msg,StatusCode status){
        this.data=null;
        this.msg=msg;
        this.status=status;
    }

}
