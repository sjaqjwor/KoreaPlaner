package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.dtos.schedule.DetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.ScheduleDto;
import wooklee.koreaplaner.mappers.ScheduleMapper;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper sm;

    @Autowired
    private UserService userService;

    public ResponseEntity<DefaultResponse> createSchedule(String email, ScheduleRequest createSchedule){
        int id = userService.findUser(1,email).getId();
        ScheduleDto createScheduleDto = new ScheduleDto().create(id,createSchedule);
        sm.createSchedule(createScheduleDto);
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,createScheduleDto.getSid());
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponse> createDetailSchedule(String email,String sid,DetailScheduleListRequest createDetailSchedulelist){
        int sidx = Integer.parseInt(sid);
        createDetailSchedulelist.getList().stream().forEach(s->new DetailScheduleDto().create(sidx,s));
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"SUCCESS_DETAILSCHEDULE_REGIST");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
    public ResponseEntity<DefaultResponse> updateSchedule(String email,String idx,ScheduleRequest updateSchedulRequest){
        int sid = Integer.parseInt(idx);
        sm.updateSchedule(new ScheduleDto().update(sid,updateSchedulRequest));
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"SUCCESS_UPDATE_SCHEDULE");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
//    public ResponseEntity<DefaultResponse> udateScheduleDetail(String email,String idx,DetailScheduleListRequest detailScheduleListRequest){
//        int sid = Integer.parseInt(idx);
//        detailScheduleListRequest.getList().stream().filter(DetailScheduleRequest::isCheck).forEach(
//                s -> sm.updateDetailSchedule(new DetailScheduleDto().update(s))
//        );
//
//    }
}
