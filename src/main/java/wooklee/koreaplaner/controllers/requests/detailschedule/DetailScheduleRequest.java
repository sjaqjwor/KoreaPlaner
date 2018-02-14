package wooklee.koreaplaner.controllers.requests.detailschedule;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetailScheduleRequest {
    private int sequence;
    private String city;
    private double latitude;
    private double longitude;
    private String traffic;
    private String startdate;
    private String enddate;
    private boolean check;

}
