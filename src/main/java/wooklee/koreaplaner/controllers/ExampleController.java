package wooklee.koreaplaner.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.mappers.CoordinateMapper;
import wooklee.koreaplaner.mappers.UserMapper;

import java.util.List;

@Api
@RestController
@RequestMapping("/exam")
public class ExampleController {
    @Autowired
    private UserMapper um;
    @Autowired
    private CoordinateMapper cm;

    @GetMapping(value = "/exam")
    public ResponseEntity<DefaultResponse> examController(){
        List<User> user = um.findAll();
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.FAIL,user);
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

    @PostMapping(value = "/examxy")
    public ResponseEntity<DefaultResponse> exampleXY(@RequestBody Examplexy examplexy){
        cm.add(examplexy);
        DefaultResponse dr = new DefaultResponse(DefaultResponse.Status.SUCCESS,"성공");
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }
}
