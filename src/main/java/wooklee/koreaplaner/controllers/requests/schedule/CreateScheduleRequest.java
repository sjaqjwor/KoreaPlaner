package wooklee.koreaplaner.controllers.requests.schedule;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleRequest {
    private String title;
    private String startdate;
    private String enddate;
    private String thema;
}
