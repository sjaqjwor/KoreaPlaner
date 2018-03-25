package wooklee.koreaplaner.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;

import java.util.List;

@Mapper
@Repository
public interface ScheduleMapper {
    Integer createSchedule(ScheduleDto createScheduleDto);
    void updateSchedule(ScheduleDto updateScheduleDto);
    List<FindUserDto> getUserSchedule(Long idx);
    List<ScheduleDto> getSchedules(Long idx);
    ScheduleDto getSchedule(Long idx);
    void deleteSchedule(Long idx);
    void updateaddFriend(@Param("uid")Long uid,@Param("sid") Long sid);
}
