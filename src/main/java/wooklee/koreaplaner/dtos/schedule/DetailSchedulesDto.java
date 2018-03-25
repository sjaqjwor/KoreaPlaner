package wooklee.koreaplaner.dtos.schedule;

import lombok.*;
import wooklee.koreaplaner.dtos.user.FindUserDto;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailSchedulesDto {
    List<FindUserDto> findUserDtoList;
    List<DetailScheduleDto> detailScheduleDtos;
}
