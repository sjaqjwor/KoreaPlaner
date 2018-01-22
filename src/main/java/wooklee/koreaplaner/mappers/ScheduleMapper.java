package wooklee.koreaplaner.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.dtos.schedule.CreateDetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.CreateScheduleDto;

@Mapper
@Repository
public interface ScheduleMapper {
    void createSchedule(CreateScheduleDto createScheduleDto);
    void createDetailSchedule(CreateDetailScheduleDto createDetailScheduleDto);
}
