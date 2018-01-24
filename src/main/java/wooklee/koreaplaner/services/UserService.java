package wooklee.koreaplaner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.configs.aws.S3Service;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.controllers.responses.DefaultResponse.Status;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.dtos.user.AddUserDto;
import wooklee.koreaplaner.dtos.user.ConfirmUserDto;
import wooklee.koreaplaner.dtos.user.FindUserDto;
import wooklee.koreaplaner.mappers.UserMapper;
import wooklee.koreaplaner.utiles.ErrorStrings;
import java.io.IOException;
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


    //security
    //    @Autowired
    //    private AuthenticationManager authenticationManager;
    //    @Autowired
    //    private UserDetailsService userDetailsService;

    //security 사용 하지만 바꿈
//    public ResponseEntity<DefaultResponse> userLogin(UserLogin userLogin){
//
//        UsernamePasswordAuthenticationToken us = new UsernamePasswordAuthenticationToken(
//                userLogin.getEmail(), userLogin.getPassword()
//        );
//        try {
//            final Authentication authentication = authenticationManager.authenticate(us);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
//            String token = jwtUtil.createToken(userDetails);
//            DefaultResponse dr = new DefaultResponse(Status.SUCCESS,token);
//            return new ResponseEntity<>(dr,HttpStatus.OK);
//
//        }catch (Exception e){
//            DefaultResponse dr = new DefaultResponse(Status.FAIL,ErrorStrings.EMAIL_OR_PASSWORD_WRONG);
//            return new ResponseEntity<>(dr,HttpStatus.UNAUTHORIZED);
//        }
//
//    }
    public ResponseEntity<DefaultResponse> userLogin(UserLoginRequest userLogin){
            Optional<FindUserDto> user = Optional.ofNullable(um.confirmUser(new ConfirmUserDto().confirmUserDto(userLogin)));
            if(user.isPresent()) {
                String token = jwtUtil.createToken(user.get());
                DefaultResponse dr = new DefaultResponse(Status.SUCCESS, token);
                return new ResponseEntity<>(dr, HttpStatus.OK);
            }
            else{
                DefaultResponse dr = new DefaultResponse(Status.FAIL,ErrorStrings.EMAIL_OR_PASSWORD_WRONG);
                return new ResponseEntity<>(dr,HttpStatus.BAD_REQUEST);
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






    //1 email
    //3 name
    public <T>FindUserDto findUser(int index,T t){
        if(index ==1){
            FindUserDto findUserDto = findByEmail((String)t);
            Optional<String> profileimage=Optional.ofNullable(findUserDto.getProfileimage());
            if(profileimage.isPresent()) {
                findUserDto.setProfileimage(url + profileimage.get());
            }
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
