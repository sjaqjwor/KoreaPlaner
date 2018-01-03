package wooklee.koreaplaner.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooklee.koreaplaner.domain.User.User;
import wooklee.koreaplaner.mapper.UserMapper;

@RestController
@RequestMapping("/user")
@Api
public class UserController {

    @Autowired
    private UserMapper um;

    @GetMapping(value = "/exam")
    public User examController(){
            return um.findOne(1);
    }

}
