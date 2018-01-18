package wooklee.koreaplaner.mappers;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.dtos.AddUserDto;
import wooklee.koreaplaner.dtos.FindUserDto;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> findAll();
    FindUserDto confirmEmail(String email);
    FindUserDto confirmName(String name);
    FindUserDto confirmId(int id);
    FindUserDto confirmPhoneNumber(String phoneNumber);
    void userRegist(AddUserDto addUserDto);
    User confirmUser(String email);
}
