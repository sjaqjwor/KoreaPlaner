package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.aws.S3Service;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.configs.security.Encriptor;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserMapper um;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private S3Service s3Service;

    @Value("${image.url}")
    private String url;


    public UserResponse userLogin(UserLoginRequest userLogin) throws NoSuchAlgorithmException {
        userLogin.setPassword(Encriptor.sha256(userLogin.getPassword()));
        FindUserDto findUserDto = um.confirmUser(userLogin);
        if (findUserDto == null) {
            return new UserResponse(ErrorStrings.EMAIL_OR_PASSWORD_WRONG, UserResponse.Status.NOCONTENT);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("Token", jwtUtil.createToken(findUserDto));
            map.put("User", findUserDto);
            return new UserResponse(map, "SUCCESS", UserResponse.Status.OK);
        }
    }


    public UserResponse userSignUp(UserRequest userSignUp) throws NoSuchAlgorithmException {
        if (!confirmEmail(userSignUp.getEmail())) {
            return new UserResponse(ErrorStrings.EXIST_EMAIL, UserResponse.Status.DUFLICATE);
        }
        if (!confirmPhoneNumber(userSignUp.getPhonenumber())) {
            return new UserResponse(ErrorStrings.EXIST_PHONENUMBER, UserResponse.Status.DUFLICATE);
        }
        um.userRegist(AddUserDto.addUser(userSignUp));
        return new UserResponse("SUCCESS", UserResponse.Status.OK);
    }

    public UserResponse addProfilImage(String idx, MultipartFile multipartFile) throws IOException {
        String filename = s3Service.uploadS3(multipartFile);
        FindUserDto findUserDto = um.imageUpdate(Integer.parseInt(idx), filename);
        if (findUserDto == null) {
            return new UserResponse(ErrorStrings.USER_NOT_FOUND, UserResponse.Status.NOTFOUND);
        } else {
            return new UserResponse(findUserDto, "SUCCESS", UserResponse.Status.OK);
        }
    }

    public UserResponse getUser(String idx) {
        FindUserDto findUserDto = findUser(0, idx);
        if (findUserDto == null) {
            return new UserResponse(ErrorStrings.USER_NOT_FOUND, UserResponse.Status.NOTFOUND);
        } else {
            return new UserResponse(findUserDto, "SUCCESS", UserResponse.Status.OK);
        }
    }


    public UserResponse updateUser(String idx, UserRequest updateUserRequest) throws NoSuchAlgorithmException {
        FindUserDto findUserDto = um.confirmId(Integer.parseInt(idx));
        if (findUserDto == null) {
            return new UserResponse(ErrorStrings.USER_NOT_FOUND, UserResponse.Status.NOTFOUND);
        } else {
            findUserDto=um.userUpdate(AddUserDto.updateUser(findUserDto, updateUserRequest, Encriptor.sha256(updateUserRequest.getPassword())));
            return new UserResponse(findUserDto, "SUCCESS", UserResponse.Status.OK);
        }
    }


    //0 id
    //1 email
    //3 name
    public <T> FindUserDto findUser(int index, T t) {
        if (index == 0) {
            FindUserDto findUserDto = findById(Integer.parseInt((String) t));
            if (findUserDto == null) {
                return null;
            } else {
                return ofUser(findUserDto);
            }
        }
        if (index == 1) {
            FindUserDto findUserDto = findByEmail((String) t);
            if (findUserDto == null) {
                return null;
            } else {
                return ofUser(findUserDto);
            }
        } else {
            String name = (String) t;
            return findByName(name);
        }

    }


    private FindUserDto ofUser(FindUserDto findUserDto) {
        Optional<String> profileimage = Optional.ofNullable(findUserDto.getProfileimage());
        if (profileimage.isPresent()) {
            findUserDto.setProfileimage(url + profileimage.get());
        }
        return findUserDto;
    }

    private boolean confirmEmail(String email) {
        FindUserDto user = um.confirmEmail(email);
        return user == null ? true : false;
    }

    private boolean confirmPhoneNumber(String phoneNumber) {
        FindUserDto user = um.confirmPhoneNumber(phoneNumber);
        return user == null ? true : false;
    }

    private FindUserDto findById(int id) {
        return um.confirmId(id);
    }

    private FindUserDto findByEmail(String email) {
        return um.confirmEmail(email);
    }

    private FindUserDto findByName(String name) {
        return um.confirmName(name);
    }


}
