package wooklee.koreaplaner.controllers;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @PostMapping(value = "/schedule")
    public ResponseEntity<DefaultResponse> addSchedule(@Valid @RequestBody ScheduleRequest createSchedule, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.createSchedule(email,createSchedule);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PostMapping(value = "/{idx}/detail")
    public ResponseEntity<DefaultResponse> addScheduleDetail(@PathVariable(value = "idx") String id, @RequestBody DetailScheduleListRequest detailScheduleListRequest, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.createDetailSchedule(email,id,detailScheduleListRequest);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/{idx}")
    public ResponseEntity<DefaultResponse> updateSchedule(@PathVariable(value = "idx") String id, @Valid @RequestBody ScheduleRequest updateSchedulRequest, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.updateSchedule(email,id,updateSchedulRequest);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/schedule/{idx}/detail")
    public ResponseEntity<DefaultResponse> updateScheduleDetail(@PathVariable(value = "idx") String idx, @RequestBody DetailScheduleListRequest detailScheduleListRequest, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.createDetailSchedule(email,idx,detailScheduleListRequest);
    }






}
