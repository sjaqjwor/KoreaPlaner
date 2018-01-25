package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private int sid;
    private int uid;
    private String title;
    private String startdate;
    private String enddate;
    private String thema;
    private int share;
    private int favorite;

    public ScheduleDto create(int uid,ScheduleRequest createSchedule){
        return builder()
                .title(createSchedule.getTitle())
                .startdate(createSchedule.getStartdate())
                .enddate(createSchedule.getEnddate())
                .thema(createSchedule.getThema())
                .uid(uid)
                .share(0)
                .favorite(0)
                .build();
    }

    public ScheduleDto update(int sid,ScheduleRequest scheduleRequest){
        return builder()
               .sid(sid)
               .startdate(scheduleRequest.getStartdate())
               .enddate(scheduleRequest.getEnddate())
               .thema(scheduleRequest.getThema())
               .title(scheduleRequest.getTitle())
               .build();
    }
}
