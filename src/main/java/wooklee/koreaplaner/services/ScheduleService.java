package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wooklee.koreaplaner.controllers.requests.schedule.CreateScheduleRequest;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.dtos.schedule.CreateDetailScheduleDto;
import wooklee.koreaplaner.dtos.schedule.CreateScheduleDto;
import wooklee.koreaplaner.mappers.ScheduleMapper;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper sm;

    @Autowired
    private UserService userService;

    public ResponseEntity<DefaultResponse> createSchedule(String email, CreateScheduleRequest createSchedule){
        int id = userService.findUser(1,email).getId();
        CreateScheduleDto createScheduleDto = new CreateScheduleDto().create(id,createSchedule);
        sm.createSchedule(createScheduleDto);
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,createScheduleDto.getSid());
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponse> createDetailSchedule(String email,int sid,List<DetailScheduleRequest> list){
        int index=1;
        for(DetailScheduleRequest d : list){
            sm.createDetailSchedule(new CreateDetailScheduleDto().create(index,sid,d));
            index++;
        }
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"SUCCESS_DETAILSCHEDULE_REGIST");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
}
