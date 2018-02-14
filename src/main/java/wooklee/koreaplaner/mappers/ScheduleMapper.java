package wooklee.koreaplaner.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;

import java.util.List;

@Mapper
@Repository
public interface ScheduleMapper {
    void createSchedule(ScheduleDto createScheduleDto);
    void updateSchedule(ScheduleDto updateScheduleDto);
    List<ScheduleDto> getSchedules(Integer idx);
    ScheduleDto getSchedule(Integer idx);
}
