package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.detailschedule.DetailScheduleRequest;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailScheduleDto {
    private int sid;
    private int id;
    private String city;
    private String traffic;
    private String startdate;
    private String enddate;
    private int sequence;

    public static DetailScheduleDto create(int sid,DetailScheduleRequest detailScheduleRequest){
        return builder()
                .city(detailScheduleRequest.getCity())
                .sid(sid)
                .enddate(detailScheduleRequest.getEnddate())
                .startdate(detailScheduleRequest.getStartdate())
                .traffic(detailScheduleRequest.getTraffic())
                .sequence(detailScheduleRequest.getSequence())
                .build();
    }
    public static DetailScheduleDto update(Integer sid,DetailScheduleRequest detailScheduleRequest){
        return builder()
                .sid(sid)
                .city(detailScheduleRequest.getCity())
                .enddate(detailScheduleRequest.getEnddate())
                .startdate(detailScheduleRequest.getStartdate())
                .traffic(detailScheduleRequest.getTraffic())
                .sequence(detailScheduleRequest.getSequence())
                .build();
    }
}
