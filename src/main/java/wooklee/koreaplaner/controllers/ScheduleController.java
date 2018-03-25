package wooklee.koreaplaner.controllers;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.detailschedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.DetailScheduleResponse;
import wooklee.koreaplaner.controllers.responses.ScheduleResponse;
import wooklee.koreaplaner.controllers.responses.StatusCode;
import wooklee.koreaplaner.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/schedules")
@Api
public class ScheduleController {

    @Autowired
    private ScheduleService ss;

    @Value("${jwt.header}")
    private String header;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 만들기", notes = "스케줄 만들기")
    @PostMapping(value = "/schedule")
    public ResponseEntity<ScheduleResponse> addSchedule(@Valid @RequestBody ScheduleRequest createSchedule) {
        ScheduleResponse sr = ss.createSchedule(createSchedule);
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 지우기", notes = "스케줄 지우기")
    @DeleteMapping(value = "/schedule/{idx}")
    public ResponseEntity<ScheduleResponse> deleteSchedule(@PathVariable("idx")Long idx) {
        ss.deleteSchedule(idx);
        return new ResponseEntity<>(ScheduleResponse.builder().msg("SUCCESS").status(StatusCode.OK).build(), HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "detail스케줄 만들기", notes = "detail스케줄 만들기")
    @PostMapping(value = "/{idx}/detail")
    public ResponseEntity<DetailScheduleResponse> addScheduleDetail(@PathVariable(value = "idx") String id, @RequestBody DetailScheduleListRequest detailScheduleListRequest){
        ss.createDetailSchedule(id,detailScheduleListRequest);
        DetailScheduleResponse sr = DetailScheduleResponse.builder().msg("SUCCESS").status(StatusCode.OK).build();
        return new ResponseEntity<DetailScheduleResponse>(sr, HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 변경하기", notes = "스케줄 변경하기")
    @PutMapping(value = "/{idx}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable(value = "idx") String id, @Valid @RequestBody ScheduleRequest updateSchedulRequest){
        ss.updateSchedule(id,updateSchedulRequest);
        ScheduleResponse sr = ScheduleResponse.builder().msg("SUCCESS").status(StatusCode.OK).build();
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/schedule/{idx}/detail")
    @ApiOperation(value = "detail스케줄 변경하기", notes = "detail스케줄 변경하기")
    public ResponseEntity<DetailScheduleResponse> updateScheduleDetail(@PathVariable(value = "idx") String idx, @RequestBody DetailScheduleListRequest detailScheduleListRequest){
        ss.updateDetailSchedule(idx,detailScheduleListRequest);
        return new ResponseEntity<>(DetailScheduleResponse.builder().msg("SUCCESS").build(),HttpStatus.OK );
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 보여주기", notes = "스케줄 보여주기(user id로)")
    @GetMapping(value = "/{uidx}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable(value = "uidx") String idx,HttpServletRequest httpServletRequest){
        ScheduleResponse sr= ss.getSchedules(idx);
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄detail 보여주기", notes = "스케줄detail 보여주기")
    @GetMapping(value = "/{idx}/detail")
    public ResponseEntity<DetailScheduleResponse> getScheduleDetail(@PathVariable(value = "idx") String idx,HttpServletRequest httpServletRequest){
        DetailScheduleResponse dsr = ss.getScheduleDetail(idx);
        return new ResponseEntity<>(dsr,HttpStatus.OK);
    }
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
//                    dataType = "string", paramType = "Header")
//    })
//    @ApiOperation(value = "스케줄에 친구추가", notes = "스케줄에 친구추가")
//    @PostMapping(value = "/{idx}/detail")
//    public ResponseEntity<DetailScheduleResponse> getScheduleDetail(@PathVariable(value = "uidx") String uidx,HttpServletRequest httpServletRequest){
//        DetailScheduleResponse dsr = ss.getScheduleDetail(idx);
//        return new ResponseEntity<>(dsr,HttpStatus.OK);
//    }
}
