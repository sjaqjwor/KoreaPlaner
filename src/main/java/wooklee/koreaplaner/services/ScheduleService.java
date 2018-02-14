package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wooklee.koreaplaner.controllers.requests.detailschedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DetailScheduleResponse;
import wooklee.koreaplaner.controllers.responses.ScheduleResponse;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;
import wooklee.koreaplaner.mappers.DetailScheduleMapper;
import wooklee.koreaplaner.mappers.ScheduleMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper sm;

    @Autowired
    private DetailScheduleMapper rsm;

    @Autowired
    private UserService userService;

    public ScheduleResponse createSchedule(ScheduleRequest createSchedule){
        ScheduleDto createScheduleDto = ScheduleDto.create(createSchedule);
        sm.createSchedule(createScheduleDto);
        return new ScheduleResponse(createScheduleDto.getSid(),"SUCCESS", ScheduleResponse.Status.OK);

    }

    public DetailScheduleResponse createDetailSchedule(String sid,DetailScheduleListRequest createDetailSchedulelist){
        int sidx = Integer.parseInt(sid);
        if(sm.getSchedule(sidx)==null){
            return new DetailScheduleResponse(ErrorStrings.Schedule_NOT_FOUND, DetailScheduleResponse.Status.NOTFOUND);
        }
        createDetailSchedulelist.getList().stream().forEach(s->rsm.createDetailSchedule(DetailScheduleDto.create(sidx,s)));
        return new DetailScheduleResponse("SUCCESS", DetailScheduleResponse.Status.OK);
    }

    public ScheduleResponse updateSchedule(String sid,ScheduleRequest updateSchedulRequest){
        int sidx = Integer.parseInt(sid);
        if(sm.getSchedule(sidx)==null){
            return new ScheduleResponse(ErrorStrings.Schedule_NOT_FOUND, ScheduleResponse.Status.NOTFOUND);
        }
        sm.updateSchedule(ScheduleDto.update(sidx,updateSchedulRequest));
        return new ScheduleResponse("SUCCESS", ScheduleResponse.Status.OK);
    }
    public DetailScheduleResponse updateDetailSchedule(String sid,DetailScheduleListRequest createDetailSchedulelist){
        int sidx = Integer.parseInt(sid);
        if(sm.getSchedule(sidx)==null){
            return new DetailScheduleResponse(ErrorStrings.Schedule_NOT_FOUND, DetailScheduleResponse.Status.NOTFOUND);
        }
        rsm.detailScheduleDelete(Integer.parseInt(sid));
        createDetailSchedulelist.getList().stream().forEach(s->rsm.createDetailSchedule(DetailScheduleDto.update(sidx,s)));

        return new DetailScheduleResponse("SUCCESS", DetailScheduleResponse.Status.OK);
    }
    public ScheduleResponse getSchedules(String idx){
        int sidx = Integer.parseInt(idx);
        List<ScheduleDto> list =Optional.ofNullable(sm.getSchedules(sidx)).orElse(new ArrayList<>());
        return new ScheduleResponse(list,"SUCCESS", ScheduleResponse.Status.OK);
    }
    public DetailScheduleResponse getScheduleDetail(String sid){
        int sidx = Integer.parseInt(sid);
        if(sm.getSchedule(sidx)==null){
            return new DetailScheduleResponse(ErrorStrings.Schedule_NOT_FOUND, DetailScheduleResponse.Status.NOTFOUND);
        }
        List<DetailScheduleDto> detailScheduleDtos=Optional.ofNullable(rsm.getScheduleDetail(sidx)).orElse(new ArrayList<>());
        return new DetailScheduleResponse(detailScheduleDtos,"SUCCESS", DetailScheduleResponse.Status.OK);
    }

}
