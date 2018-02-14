package wooklee.koreaplaner.controllers.requests.detailschedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DetailScheduleListRequest {
    List<DetailScheduleRequest> list;
}
