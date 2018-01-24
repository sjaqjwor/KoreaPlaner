package wooklee.koreaplaner.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.DefaultResponse.Status;
import wooklee.koreaplaner.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value="/api/user")
@Api
public class UserController {

    @Autowired
    private UserService us;

    @Value("${jwt.header}")
    private String header;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping(value="/login")
    public ResponseEntity<DefaultResponse> userLogin(@RequestBody UserLoginRequest userLogin){
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
    @GetMapping(value = "/find")
    public ResponseEntity<DefaultResponse> findUser(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        DefaultResponse dr = new DefaultResponse(Status.SUCCESS,us.findUser(1,email));
        return new ResponseEntity<>(dr,HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })

    @PutMapping(value = "/add/profilimage")
    public ResponseEntity<DefaultResponse> addProfilImage(HttpServletRequest httpServletRequest,@RequestParam("profilImage")MultipartFile multipartFile) throws IOException{
        String token = httpServletRequest.getHeader(header);
        String email = jwtUtil.getEmailFromToken(token);
        return us.addProfilImage(email,multipartFile);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value ="authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/add/interest")
    public ResponseEntity<DefaultResponse> addInterest(@RequestParam("interest") String interest,HttpServletRequest httpServletRequest){
      String token = httpServletRequest.getHeader(header);
      String email = jwtUtil.getEmailFromToken(token);
      return us.addInterest(email,interest);
    }




}
