package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.aws.S3Service;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.user.UserLogin;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.DefaultResponse.Status;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.dtos.AddUserDto;
import wooklee.koreaplaner.dtos.FindUserDto;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserMapper um;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${image.url}")
    private String url;

    public ResponseEntity<DefaultResponse> userLogin(UserLogin userLogin){

        UsernamePasswordAuthenticationToken us = new UsernamePasswordAuthenticationToken(
                userLogin.getEmail(), userLogin.getPassword()
        );
        try {
            final Authentication authentication = authenticationManager.authenticate(us);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            String token = jwtUtil.createToken(userDetails);
            DefaultResponse dr = new DefaultResponse(Status.SUCCESS,token);
            return new ResponseEntity<>(dr,HttpStatus.OK);

        }catch (Exception e){
            DefaultResponse dr = new DefaultResponse(Status.FAIL,ErrorStrings.EMAIL_OR_PASSWORD_WRONG);
            return new ResponseEntity<>(dr,HttpStatus.UNAUTHORIZED);
        }

    }

    public ResponseEntity<DefaultResponse> userSignUp(UserSignUp userSignUp)throws IOException{
        if(!confirmEmail(userSignUp.getEmail())){
            return new ResponseEntity<>(new DefaultResponse(Status.FAIL,ErrorStrings.EXIST_EMAIL), HttpStatus.BAD_REQUEST);
        }
        if(!confirmPhoneNumber(userSignUp.getPhonenumber())){
            return new ResponseEntity<>(new DefaultResponse(Status.FAIL,ErrorStrings.EXIST_PHONENUMBER),HttpStatus.BAD_REQUEST);
        }
//        if(multipartFile==null){
//            AddUserDto addUserDto = new AddUserDto(userSignUp);
//            addUserDto.setProfileimage(ErrorStrings.IMAGE_IS_NULL);
//            um.userRegist(addUserDto);
//            //return new ResponseEntity<>(new DefaultResponse(Status.FAIL,ErrorStrings.IMAGE_IS_NULL),HttpStatus.SERVICE_UNAVAILABLE);
//        }else {
            //String filename = s3Service.uploadS3(multipartFile);
            AddUserDto addUserDto = new AddUserDto(userSignUp);
            //addUserDto.setProfileimage(filename);
            um.userRegist(addUserDto);

        return new ResponseEntity<>(new DefaultResponse(Status.SUCCESS,"USER_REGIST_SUCCESS"),HttpStatus.OK);
    }

    //0 ids
    //1 email
    //3 name
    public <T>FindUserDto findUser(int id,T t){
        if(id==0){
            int i  = (Integer)t;
            return findById(i);
        }else if(id ==1){
            String email = jwtUtil.getEmailFromToken((String)t);
            FindUserDto findUserDto = findByEmail(email);
            String profileimage=findUserDto.getProfileimage();
            findUserDto.setProfileimage(url+profileimage);
            return findUserDto;
        }else{
            String name = (String)t;
            return findByName(name);
        }

    }

    private boolean confirmEmail(String email){
        FindUserDto user = um.confirmEmail(email);
        return user == null ? true : false;
    }

    private boolean confirmPhoneNumber(String phoneNumber){
        FindUserDto user = um.confirmPhoneNumber(phoneNumber);
        return user == null ? true : false;
    }

    private FindUserDto findById(int id){
        return um.confirmId(id);
    }
    private FindUserDto findByEmail(String email){
        return um.confirmEmail(email);
    }
    private FindUserDto findByName(String name){
        return um.confirmName(name);
    }



}
