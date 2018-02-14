package wooklee.koreaplaner.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutScheduleDto {
    String city;
    Double latitude;
    Double longitude;
    Integer sequence;
    String traffic;
    String startdate;
    String enddate;
}
