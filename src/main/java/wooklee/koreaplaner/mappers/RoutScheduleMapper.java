package wooklee.koreaplaner.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.RoutScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;

import java.util.List;

@Mapper
@Repository
public interface RoutScheduleMapper {
    void updateDetailSchedule(DetailScheduleDto updateDetailScheduleDto);
    List<RoutScheduleDto> getScheduleDetail(Integer idx);
}
