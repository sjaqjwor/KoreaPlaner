package wooklee.koreaplaner.controllers.requests.detailschedule;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetailScheduleRequest {
    @NotNull
    private int sequence;
    @NotNull
    private String city;
    private String traffic;
    private String startdate;
    private String enddate;
}
