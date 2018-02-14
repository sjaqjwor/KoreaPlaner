package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.RoutScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;
import wooklee.koreaplaner.mappers.RoutScheduleMapper;
import wooklee.koreaplaner.mappers.ScheduleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper sm;

    @Autowired
    private RoutScheduleMapper rsm;

    @Autowired
    private UserService userService;

    public ResponseEntity<DefaultResponse> createSchedule(String idx, ScheduleRequest createSchedule){
        int id = userService.findUser(0,idx).getId();
        ScheduleDto createScheduleDto = ScheduleDto.create(id,createSchedule);
        sm.createSchedule(createScheduleDto);
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,createScheduleDto.getSid());
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponse> createDetailSchedule(String sid,DetailScheduleListRequest createDetailSchedulelist){
        int sidx = Integer.parseInt(sid);
        createDetailSchedulelist.getList().stream().forEach(s->sm.createDetailSchedule(DetailScheduleDto.create(sidx,s)));
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"SUCCESS_DETAILSCHEDULE_REGIST");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
    public ResponseEntity<DefaultResponse> updateSchedule(String idx,ScheduleRequest updateSchedulRequest){
        int sid = Integer.parseInt(idx);
        sm.updateSchedule(ScheduleDto.update(sid,updateSchedulRequest));
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"SUCCESS_UPDATE_SCHEDULE");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
    public ResponseEntity<DefaultResponse> getSchedule(String idx){
        int sidx = Integer.parseInt(idx);
        List<ScheduleDto> list =Optional.ofNullable(sm.getSchedule(sidx)).orElse(new ArrayList<>());
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,list);
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
    public ResponseEntity<DefaultResponse> getScheduleDetail(String idx){
        int sidx = Integer.parseInt(idx);
        List<RoutScheduleDto> list =Optional.ofNullable(rsm.getScheduleDetail(sidx)).orElse(new ArrayList<>());
        System.err.println(list.size());
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,list);
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

}
