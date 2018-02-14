package wooklee.koreaplaner.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;

import wooklee.koreaplaner.dtos.schedule.ScheduleDto;

import java.util.List;

@Mapper
@Repository
public interface DetailScheduleMapper {
    void createDetailSchedule(DetailScheduleDto createDetailScheduleDto);
    void updateDetailSchedule(DetailScheduleDto updateDetailScheduleDto);
    List<DetailScheduleDto> getScheduleDetail(Integer idx);
    void detailScheduleDelete(Integer idx);
}
