package wooklee.koreaplaner.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;

import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/api/users")
@Api
public class UserController {

    @Autowired
    private UserService us;

    @Value("${jwt.header}")
    private String header;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation(value = "login", notes = "login")
    @PostMapping(value = "/login")
    public ResponseEntity<UserResponse> userLogin(@Valid @RequestBody UserLoginRequest userLogin) throws NoSuchAlgorithmException {
        UserResponse ur = us.userLogin(userLogin);
        return new ResponseEntity<>(ur, HttpStatus.valueOf(ur.getStatus().getCode()));

    }

    @PostMapping(value = "sign/up")
    @ApiOperation(value = "회원가입", notes = "회원가입")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserRequest userSignUp)
            throws NoSuchAlgorithmException {
        UserResponse ur = us.userSignUp(userSignUp);
        return new ResponseEntity<>(ur, HttpStatus.valueOf(ur.getStatus().getCode()));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "회원정보", notes = "회원정보")
    @GetMapping(value = "/{idx}")
    public ResponseEntity<UserResponse> findUser(@PathVariable("idx") String idx) {
        UserResponse ur = us.getUser(idx);
        return new ResponseEntity<>(ur, HttpStatus.valueOf(ur.getStatus().getCode()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "profile image", notes = "profile image")
    @PutMapping(value = "/{idx}/profilimage")
    public ResponseEntity<UserResponse> addProfilImage(@PathVariable("idx") String idx, @NotNull @RequestParam("profilImage") MultipartFile multipartFile) throws IOException {
        UserResponse ur = us.addProfilImage(idx, multipartFile);
        return new ResponseEntity<>(ur, HttpStatus.valueOf(ur.getStatus().getCode()));
    }


    @PutMapping(value = "/{idx}")
    @ApiOperation(value = "회원정보 수정 ", notes = "회원정보수정")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("idx") String idx, @Valid @RequestBody UserRequest userRequest) throws NoSuchAlgorithmException{
        System.err.println(idx);
        UserResponse ur= us.updateUser(idx, userRequest);
        return new ResponseEntity<>(ur, HttpStatus.valueOf(ur.getStatus().getCode()));
    }
}
