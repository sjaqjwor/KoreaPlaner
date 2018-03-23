package wooklee.koreaplaner.mappers;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.controllers.requests.friend.AddFriendRequest;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.dtos.friend.AddFriendDto;
import wooklee.koreaplaner.dtos.friend.FindFriendDto;
import wooklee.koreaplaner.dtos.friend.UpdateFriendDto;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    List<FindFriendDto> findByName(String name);
    void addFriend(AddFriendDto afd);
    void acceptFriend(UpdateFriendDto ufd);
    List<FindFriendDto> friendListbyUid(String idx);
    List<FindFriendDto> friendListbyFid(String idx);
    List<FindFriendDto> receiveList(String idx);
}
