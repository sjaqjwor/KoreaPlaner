package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleRequest;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDetailScheduleDto {
    private int sid;
    private String city;
    private double latitude;
    private double longitude;
    private String traffic;
    private String startdate;
    private String enddate;
    private int sequence;

    public CreateDetailScheduleDto create(int sequence,int sid,DetailScheduleRequest detailScheduleRequest){
        return builder()
                .city(detailScheduleRequest.getCity())
                .sid(sid)
                .enddate(detailScheduleRequest.getEnddate())
                .startdate(detailScheduleRequest.getStartdate())
                .traffic(detailScheduleRequest.getTraffic())
                .latitude(detailScheduleRequest.getLatitude())
                .longitude(detailScheduleRequest.getLongitude())
                .sequence(sequence)
                .build();
    }
}
