package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooklee.koreaplaner.controllers.requests.detailschedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DetailScheduleResponse;
import wooklee.koreaplaner.controllers.responses.ScheduleResponse;
import wooklee.koreaplaner.controllers.responses.StatusCode;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.DetailSchedulesDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.exceptions.ScheduleNotFoundException;
import wooklee.koreaplaner.mappers.DetailScheduleMapper;
import wooklee.koreaplaner.mappers.ScheduleMapper;
import wooklee.koreaplaner.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleMapper sm;

    @Autowired
    private DetailScheduleMapper rsm;

    @Value("${image.url}")
    private String url;


    public ScheduleResponse createSchedule(ScheduleRequest createSchedule) {
        ScheduleDto createScheduleDto = ScheduleDto.create(createSchedule);
        Integer sid = sm.createSchedule(createScheduleDto);
        return new ScheduleResponse(sid, "SUCCESS", StatusCode.OK);
    }

    public void deleteSchedule(Long idx){
        Optional.ofNullable(sm.getSchedule(idx)).orElseThrow(ScheduleNotFoundException::new);
        sm.deleteSchedule(idx);
    }

    public void createDetailSchedule(String sid, DetailScheduleListRequest createDetailSchedulelist) {
        Long sidx = Long.parseLong(sid);
        if (sm.getSchedule(sidx) == null) {
            throw new ScheduleNotFoundException();
        }
        createDetailSchedulelist.getList().stream().forEach(s -> rsm.createDetailSchedule(DetailScheduleDto.create(sidx, s)));
    }

    public void updateSchedule(String sid, ScheduleRequest updateSchedulRequest) {
        Long sidx = Long.parseLong(sid);
        if (sm.getSchedule(sidx) == null) {
            throw new ScheduleNotFoundException();
        }
        sm.updateSchedule(ScheduleDto.update(sidx, updateSchedulRequest));
    }

    public void updateDetailSchedule(String sid, DetailScheduleListRequest createDetailSchedulelist) {
        Long sidx = Long.parseLong(sid);

        if (sm.getSchedule(sidx) == null) {
            throw new ScheduleNotFoundException();
        }
        rsm.detailScheduleDelete(sidx);
        createDetailSchedulelist.getList().stream().forEach(s -> rsm.createDetailSchedule(DetailScheduleDto.update(sidx, s)));
    }

    public ScheduleResponse getSchedules(String idx) {
        Long sidx = Long.parseLong(idx);
        List<ScheduleDto> list = Optional.ofNullable(sm.getSchedules(sidx)).orElse(new ArrayList<>());
        return new ScheduleResponse(list, "SUCCESS", StatusCode.OK);
    }

    public DetailScheduleResponse getScheduleDetail(String sid) {
        Long sidx = Long.parseLong(sid);
        if (sm.getSchedule(sidx) == null) {
            throw new ScheduleNotFoundException();
        }
        List<FindUserDto> findUserDtos = Optional.ofNullable(sm.getUserSchedule(Long.parseLong(sid)))
                .orElse(new ArrayList<>())
                .stream().map(s -> {if(s.getProfileimage()!=null){
                    s.setProfileimage(url+s.getProfileimage());
                }return s;}).collect(Collectors.toList());
        List<DetailScheduleDto> detailScheduleDtos = Optional.ofNullable(rsm.getScheduleDetail(sidx)).orElse(new ArrayList<>());
        return new DetailScheduleResponse(new DetailSchedulesDto(findUserDtos,detailScheduleDtos), "SUCCESS", StatusCode.OK);
    }

}
