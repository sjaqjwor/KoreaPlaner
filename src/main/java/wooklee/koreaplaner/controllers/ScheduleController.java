package wooklee.koreaplaner.controllers;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
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
    public ResponseEntity<DefaultResponse> addSchedule(@Valid @RequestBody ScheduleRequest createSchedule, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(header);
        String id = jwtUtil.getIdFromToken(token);
        return ss.createSchedule(id,createSchedule);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "detail스케줄 만들기", notes = "detail스케줄 만들기")
    @PostMapping(value = "/{idx}/detail")
    public ResponseEntity<DefaultResponse> addScheduleDetail(@PathVariable(value = "idx") String id, @RequestBody DetailScheduleListRequest detailScheduleListRequest, HttpServletRequest httpServletRequest){
        return ss.createDetailSchedule(id,detailScheduleListRequest);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 변경하기", notes = "스케줄 변경하기")
    @PutMapping(value = "/{idx}")
    public ResponseEntity<DefaultResponse> updateSchedule(@PathVariable(value = "idx") String id, @Valid @RequestBody ScheduleRequest updateSchedulRequest, HttpServletRequest httpServletRequest){
        return ss.updateSchedule(id,updateSchedulRequest);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/schedule/{idx}/detail")
    @ApiOperation(value = "detaul스케줄 변경하기", notes = "detail스케줄 변경하기 아직 미완성 너랑 상의하할꺼 있음..")
    public ResponseEntity<DefaultResponse> updateScheduleDetail(@PathVariable(value = "idx") String idx, @RequestBody DetailScheduleListRequest detailScheduleListRequest, HttpServletRequest httpServletRequest){
        return ss.createDetailSchedule(idx,detailScheduleListRequest);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄 보여주기", notes = "스케줄 보여주기(user id로)")
    @GetMapping(value = "/{uidx}")
    public ResponseEntity<DefaultResponse> getSchedule(@PathVariable(value = "uidx") String uidx,HttpServletRequest httpServletRequest){
        return ss.getSchedule(uidx);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "스케줄detail 보여주기", notes = "스케줄detail 보여주기")
    @GetMapping(value = "/{idx}/detail")
    public ResponseEntity<DefaultResponse> getScheduleDetail(@PathVariable(value = "idx") String uidx,HttpServletRequest httpServletRequest){
        return ss.getScheduleDetail(uidx);
    }
}
