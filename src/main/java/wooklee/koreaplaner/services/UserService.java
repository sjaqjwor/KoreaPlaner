package wooklee.koreaplaner.services;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.aws.S3Service;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.configs.security.Encriptor;
import wooklee.koreaplaner.controllers.requests.user.UpdateUserRequest;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserRequest;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.exceptions.UserConflictException;
import wooklee.koreaplaner.exceptions.UserNotFoundException;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper um;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private S3Service s3Service;

    @Value("${image.url}")
    private String url;


    public FindUserDto userLogin(UserLoginRequest userLogin) throws NoSuchAlgorithmException {
        userLogin.setPassword(Encriptor.sha256(userLogin.getPassword()));
        FindUserDto fud=Optional.ofNullable(um.confirmUser(userLogin))
                .map(u -> {
                    Optional.ofNullable(u.getProfileimage())
                            .ifPresent((s) -> { u.setProfileimage(url+s);});
                    return u;
                }).orElseThrow(UserNotFoundException::new);
        return fud;
    }


    public void userSignUp(UserRequest userSignUp) throws NoSuchAlgorithmException {
        if (!confirmEmail(userSignUp.getEmail())) {
            throw new UserConflictException(ErrorStrings.EXIST_EMAIL);
        }
        if (!confirmPhoneNumber(userSignUp.getPhonenumber())) {
            throw new UserConflictException(ErrorStrings.EXIST_PHONENUMBER);
        }
        um.userRegist(AddUserDto.addUser(userSignUp));

    }

    public FindUserDto addProfilImage(String idx, MultipartFile multipartFile) throws IOException {
        String filename = s3Service.uploadS3(multipartFile);
        FindUserDto fud =Optional.ofNullable(um.imageUpdate(Integer.parseInt(idx), filename)).orElseThrow(UserNotFoundException::new);
        if(fud.getProfileimage()==null){
            return fud;
        }else{
            fud.setProfileimage(url+fud.getProfileimage());
            return fud;
        }

    }

    public FindUserDto getUser(String idx) {
        return Optional.ofNullable(findUser(0, idx)).orElseThrow(UserNotFoundException::new);
    }
    public List<FindUserDto> getUserAll(String idx){
        return um.getAllUser(Long.parseLong(idx)).stream().map(s->{if(s.getProfileimage()!=null){
        s.setProfileimage(url+s.getProfileimage());}
       return s; }).collect(Collectors.toList());
    }


    public UserResponse updateUser(String idx, UpdateUserRequest updateUserRequest) throws NoSuchAlgorithmException {
        FindUserDto findUserDto = um.confirmId(Long.parseLong(idx));
        if (findUserDto == null) {
            throw new UserNotFoundException();
        }
        if (!confirmPhoneNumber(updateUserRequest, idx)) {
            throw new UserConflictException(ErrorStrings.EXIST_PHONENUMBER);
        }
        if (TextUtils.isBlank(updateUserRequest.getPassword())) {
            return UserResponse.builder().user(um.userUpdate(AddUserDto.updateUser(findUserDto, updateUserRequest))).msg("SUCCESS").status(StatusCode.OK).build();
        } else {
            return UserResponse.builder().user(um.userUpdate(AddUserDto.updateUser(findUserDto, updateUserRequest, Encriptor.sha256(updateUserRequest.getPassword())))).msg("SUCCESS").status(StatusCode.OK).build();
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

    private <T> boolean confirmPhoneNumber(T t, String... idx) {
        if (t instanceof UpdateUserRequest) {
            UpdateUserRequest updateUserRequest = (UpdateUserRequest) t;
            FindUserDto user = um.confirmId(Long.parseLong(idx[0]));
            return user.getPhonenumber().equals(updateUserRequest.getPhonenumber()) ? false : true;
        } else {
            FindUserDto user = um.confirmPhoneNumber((String) t);
            return user == null ? true : false;
        }

    }

    private FindUserDto findById(int id) {
        return um.confirmId(new Long(id));
    }

    private FindUserDto findByEmail(String email) {
        return um.confirmEmail(email);
    }

    private FindUserDto findByName(String name) {
        return um.confirmName(name);
    }


}
