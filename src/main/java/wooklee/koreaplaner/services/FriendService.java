package wooklee.koreaplaner.services;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.aws.S3Service;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.configs.security.Encriptor;
import wooklee.koreaplaner.controllers.requests.friend.AcceptFriendRequest;
import wooklee.koreaplaner.controllers.requests.friend.AddFriendRequest;
import wooklee.koreaplaner.controllers.requests.user.UpdateUserRequest;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;
import wooklee.koreaplaner.controllers.responses.FriendResponse;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.dtos.friend.AddFriendDto;
import wooklee.koreaplaner.dtos.friend.FindFriendDto;
import wooklee.koreaplaner.dtos.friend.UpdateFriendDto;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.exceptions.UserNotFoundException;
import wooklee.koreaplaner.mappers.FriendMapper;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private FriendMapper fm;
    @Autowired
    private UserMapper um;

    @Value("${image.url}")
    private String url;

    public FriendResponse findFriend(String name){
        return new FriendResponse(Optional.ofNullable(fm.findByName(name)).orElse(new ArrayList<>()));
    }
    public void addFriend(AddFriendRequest afr){
        if(um.confirmId(afr.getFidx())==null){
            throw new UserNotFoundException();
        }
        fm.addFriend(AddFriendDto.builder().uid(afr.getUidx()).fid(afr.getFidx()).build());
    }
    public void acceptFriend(AcceptFriendRequest afr){
        if(um.confirmId(afr.getFid())==null){
            throw new UserNotFoundException();
        }
        fm.acceptFriend(UpdateFriendDto.builder().uid(afr.getUid()).fid(afr.getFid()).status(afr.getStatus()).build());
    }
    public FriendResponse getFriends(String idx){
        List<FindFriendDto> temp = fm.friendListbyFid(idx);
        List<FindFriendDto> temp1 = fm.friendListbyUid(idx);
        temp.addAll(temp1);
        return new FriendResponse(temp);
    }
    public FriendResponse getReceiveFriends(String idx){
        List<FindFriendDto> friends = fm.receiveList(idx);
        return new FriendResponse(friends);
    }
}
