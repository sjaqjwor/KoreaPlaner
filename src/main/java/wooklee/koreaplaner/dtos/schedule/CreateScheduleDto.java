package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.controllers.requests.schedule.CreateScheduleRequest;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleDto {
    private int sid;
    private int uid;
    private String title;
    private String startdate;
    private String enddate;
    private String thema;
    private int share;
    private int favorite;

    public CreateScheduleDto create(int uid,CreateScheduleRequest createSchedule){
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
}
