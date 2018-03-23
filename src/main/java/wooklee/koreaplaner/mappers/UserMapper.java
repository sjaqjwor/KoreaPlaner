package wooklee.koreaplaner.mappers;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    FindUserDto confirmEmail(String email);
    FindUserDto confirmName(String name);
    FindUserDto confirmId(Long id);
    FindUserDto confirmPhoneNumber(String phoneNumber);
    void userRegist(AddUserDto addUserDto);
    FindUserDto confirmUser(UserLoginRequest userLoginRequest);
    FindUserDto imageUpdate(@Param("id") Integer uid, @Param("image") String image);
    FindUserDto userUpdate(AddUserDto addUserDto);
    List<FindUserDto> getAllUser(Long id);
}
