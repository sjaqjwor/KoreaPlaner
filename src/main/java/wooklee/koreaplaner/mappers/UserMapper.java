package wooklee.koreaplaner.mappers;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;

@Mapper
@Repository
public interface UserMapper {

    FindUserDto confirmEmail(String email);
    FindUserDto confirmName(String name);
    FindUserDto confirmId(Integer id);
    FindUserDto confirmPhoneNumber(String phoneNumber);
    void userRegist(AddUserDto addUserDto);
    FindUserDto confirmUser(UserLoginRequest userLoginRequest);
    FindUserDto imageUpdate(Integer id , String password);
    FindUserDto userUpdate(AddUserDto addUserDto);
}
