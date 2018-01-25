package wooklee.koreaplaner.controllers.requests.schedule;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    @NotNull
    private String title;
    @NotNull
    private String startdate;
    @NotNull
    private String enddate;
    @NotNull
    private String thema;
}
