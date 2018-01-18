package wooklee.koreaplaner.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.controllers.requests.user.UserLogin;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.DefaultResponse.Status;
import wooklee.koreaplaner.services.UserService;
import wooklee.koreaplaner.utiles.ErrorStrings;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/user")
@Api
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private UserMapper um;

    @Value("${jwt.header}")
    private String header;

    @GetMapping(value = "/exam")
    public ResponseEntity<DefaultResponse> examController(){
        List<User> user = um.findAll();
        DefaultResponse dr = new DefaultResponse(Status.FAIL,user);
        return new ResponseEntity<>(dr, HttpStatus.OK);
    }

    @PostMapping(value="/user/login")
    public ResponseEntity<DefaultResponse> userLogin(@RequestBody UserLogin userLogin){
        return us.userLogin(userLogin);
    }

    @PostMapping(value="sign/up")
    public ResponseEntity<DefaultResponse> signUp(@RequestBody UserSignUp userSignUp)
    throws IOException{
        return us.userSignUp(userSignUp);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @GetMapping(value = "find/user")
    public ResponseEntity<DefaultResponse> confirm(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        DefaultResponse dr = new DefaultResponse(Status.SUCCESS,us.findUser(1,token));
        return new ResponseEntity<>(dr,HttpStatus.OK);
    }



}
