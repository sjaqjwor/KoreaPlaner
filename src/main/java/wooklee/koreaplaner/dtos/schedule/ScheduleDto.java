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
    private Long sid;
    private Long uid;
    private String title;
    private String startdate;
    private String enddate;
    private String thema;
    private int share;
    private int favorite;
    private String content;

    public static ScheduleDto create(ScheduleRequest createSchedule){
        return builder()
                .title(createSchedule.getTitle())
                .startdate(createSchedule.getStartdate())
                .enddate(createSchedule.getEnddate())
                .thema(createSchedule.getThema())
                .uid(Long.parseLong(createSchedule.getUidx()))
                .share(0)
                .favorite(0)
                .content(createSchedule.getContent())
                .build();
    }

    public static ScheduleDto update(Long sid,ScheduleRequest scheduleRequest){
        return builder()
               .sid(sid)
               .startdate(scheduleRequest.getStartdate())
               .enddate(scheduleRequest.getEnddate())
               .thema(scheduleRequest.getThema())
               .title(scheduleRequest.getTitle())
               .build();
    }
}
