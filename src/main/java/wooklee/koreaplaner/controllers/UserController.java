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
import wooklee.koreaplaner.controllers.requests.user.UpdateUserRequest;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;

import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.exceptions.UserConflictException;
import wooklee.koreaplaner.exceptions.UserNotFoundException;
import wooklee.koreaplaner.services.UserService;
import wooklee.koreaplaner.utiles.ErrorStrings;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
        FindUserDto fud = us.userLogin(userLogin);
        return new ResponseEntity<>(UserResponse.builder().data(jwtUtil.createToken(fud)).user(fud).msg("SUCCESS").status(StatusCode.OK).build(), HttpStatus.OK);

    }

    @PostMapping(value = "/sign/up")
    @ApiOperation(value = "회원가입", notes = "회원가입")
    public ResponseEntity<DefaultResponse> signUp(@Valid @RequestBody UserRequest userSignUp)
            throws NoSuchAlgorithmException {
        us.userSignUp(userSignUp);
        return new ResponseEntity<DefaultResponse>(DefaultResponse.builder().status(StatusCode.OK).build(), HttpStatus.OK);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "회원정보", notes = "회원정보")
    @GetMapping(value = "/{idx}")
    public ResponseEntity<UserResponse> findUser(@PathVariable("idx") String idx) {
        FindUserDto fud = us.getUser(idx);
        UserResponse ur = UserResponse.builder().user(fud).msg("SUCCESS").status(StatusCode.OK).build();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "모든회원정보", notes = "모든회원정보 (idx는 자신꺼 그 이뉴는 자기자신 제외하고 다보냄)")
    @GetMapping(value = "/all/{idx}")
    public ResponseEntity<UserResponse> findUserAll(@PathVariable("idx") String idx) {
        List<FindUserDto> fud = us.getUserAll(idx);
        UserResponse ur = UserResponse.builder().user(fud).msg("SUCCESS").status(StatusCode.OK).build();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "profile image", notes = "profile image")
    @PutMapping(value = "/{idx}/profilimage")
    public ResponseEntity<UserResponse> addProfilImage(@PathVariable("idx") String idx, @NotNull @RequestParam("profilImage") MultipartFile multipartFile) throws IOException {
        FindUserDto fud = us.addProfilImage(idx, multipartFile);
        UserResponse ur = UserResponse.builder().status(StatusCode.OK).msg("SUCCESS").user(fud).build();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @PutMapping(value = "/{idx}")
    @ApiOperation(value = "회원정보 수정 ", notes = "회원정보수정")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("idx") String idx, @Valid @RequestBody UpdateUserRequest updateUserRequest) throws NoSuchAlgorithmException {
        UserResponse ur = us.updateUser(idx, updateUserRequest);
        return new ResponseEntity<UserResponse>(ur, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserResponse> userNotFoundException(UserNotFoundException unfe) {
        UserResponse ur = UserResponse.builder().msg(ErrorStrings.USER_NOT_FOUND).status(StatusCode.USERNOTFOUND).build();
        return new ResponseEntity<UserResponse>(ur, HttpStatus.OK);
    }

}
