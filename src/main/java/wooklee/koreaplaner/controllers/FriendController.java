package wooklee.koreaplaner.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooklee.koreaplaner.controllers.requests.friend.AcceptFriendRequest;
import wooklee.koreaplaner.controllers.requests.friend.AddFriendRequest;
import wooklee.koreaplaner.controllers.responses.FriendResponse;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.services.FriendService;

@RestController
@RequestMapping(value = "/api/")
@Api
public class FriendController {
    @Autowired
    private FriendService fs;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "친구찾기", notes = "친구찾기")
    @GetMapping(value = "/friends/{name}")
    public ResponseEntity<FriendResponse> findUser(@PathVariable("name") String name) {
        System.err.println(name);
        FriendResponse fr = fs.findFriend(name);
        return new ResponseEntity<>(fr, HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "친구추가", notes = "친구추가 uid my index and fid friend index")
    @PostMapping(value = "/friend/add")
    public ResponseEntity<FriendResponse> addFriend(@RequestBody AddFriendRequest afr) {

        fs.addFriend(afr);
        return new ResponseEntity<>(new FriendResponse("SUCCESS"), HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "친구수락", notes = "친구수락 status 1.수락 2.거절 && uid는 자기 인덱스 fid 친구 인덱스")
    @PutMapping(value = "/friend/accept")
    public ResponseEntity<FriendResponse> acceptFriend(@RequestBody AcceptFriendRequest afr) {

        fs.acceptFriend(afr);
        return new ResponseEntity<>(new FriendResponse("SUCCESS"), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "my friend list", notes = "my friend list")
    @GetMapping(value = "/{uid}/friends")
    public ResponseEntity<FriendResponse> getFriends(@PathVariable("uid") String idx) {
        FriendResponse fr = fs.getFriends(idx);
        return new ResponseEntity<>(fr, HttpStatus.OK);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "authorization header", required = true,
                    dataType = "string", paramType = "Header")
    })
    @ApiOperation(value = "receive friend list", notes = "my receive list")
    @GetMapping(value = "/{uid}/friends/receive")
    public ResponseEntity<FriendResponse> getReceiveFriends(@PathVariable("uid") String idx) {
        FriendResponse fr = fs.getReceiveFriends(idx);
        return new ResponseEntity<>(fr, HttpStatus.OK);
    }
}
