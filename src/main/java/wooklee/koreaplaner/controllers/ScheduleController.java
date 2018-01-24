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
import wooklee.koreaplaner.controllers.requests.schedule.CreateScheduleRequest;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleRequest;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="/api/schedule")
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
    @PostMapping(value = "/add")
    public ResponseEntity<DefaultResponse> addSchedule(@RequestBody CreateScheduleRequest createSchedule, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.createSchedule(email,createSchedule);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PostMapping(value = "/add/{idx}/detail")
    public ResponseEntity<DefaultResponse> addScheduleDetail(@PathVariable(value = "idx") int id, @RequestBody DetailScheduleListRequest detailScheduleListRequest, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return ss.createDetailSchedule(email,id,detailScheduleListRequest);
    }

}
