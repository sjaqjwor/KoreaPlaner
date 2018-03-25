package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.detailschedule.DetailScheduleRequest;
import wooklee.koreaplaner.dtos.user.FindUserDto;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailScheduleDto {
    private Long sid;
    private Long id;
    private String city;
    private String traffic;
    private String startdate;
    private String enddate;
    private int sequence;
    private Double longitude;
    private Double latitude;

    public static DetailScheduleDto create(Long sid,DetailScheduleRequest detailScheduleRequest){
        return builder()
                .city(detailScheduleRequest.getCity())
                .sid(sid)
                .enddate(detailScheduleRequest.getEnddate())
                .startdate(detailScheduleRequest.getStartdate())
                .traffic(detailScheduleRequest.getTraffic())
                .sequence(detailScheduleRequest.getSequence())
                .latitude(detailScheduleRequest.getLatitude())
                .longitude(detailScheduleRequest.getLongitude())
                .build();
    }
    public static DetailScheduleDto update(Long sid,DetailScheduleRequest detailScheduleRequest){
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
