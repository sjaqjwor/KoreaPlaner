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
        um.userRegist(new AddUserDto().addUser(userSignUp));

        return new ResponseEntity<>(new DefaultResponse(Status.SUCCESS,"USER_REGIST_SUCCESS"),HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponse> addProfilImage(String email , MultipartFile multipartFile) throws IOException{
        if(multipartFile==null){
            return new ResponseEntity<>(new DefaultResponse(Status.FAIL,ErrorStrings.IMAGE_IS_NULL),HttpStatus.BAD_REQUEST);
        }else{
            FindUserDto findUserDto = um.confirmEmail(email);

            String filename = s3Service.uploadS3(multipartFile);
            findUserDto.setProfileimage(filename);
            um.addUserImage(new AddUserDto().updateUser(findUserDto));
            return new ResponseEntity<>(new DefaultResponse(Status.SUCCESS,"SUCCESS_IMAGE_REGIST"),HttpStatus.OK);
        }
    }

    public ResponseEntity<DefaultResponse> addInterest(String email, String interest){
        FindUserDto findUserDto = um.confirmEmail(email);
        findUserDto.setInterest(interest);
        um.addUserInterest(new AddUserDto().updateUser(findUserDto));
        return new ResponseEntity<>(new DefaultResponse(Status.SUCCESS,"SUCCESS_INTEREST_REGIST"),HttpStatus.OK);
    }





    //0 ids
    //1 email
    //3 name
    public <T>FindUserDto findUser(int index,T t){
        if(index==0){
            int i  = (Integer)t;
            return findById(i);
        }else if(index ==1){
            FindUserDto findUserDto = findByEmail((String)t);
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
