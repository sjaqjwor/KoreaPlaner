package wooklee.koreaplaner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;


import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import wooklee.koreaplaner.controllers.UserController;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
import wooklee.koreaplaner.controllers.requests.user.UserSignUp;

import wooklee.koreaplaner.services.UserService;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    private MockMvc mockMvc;


    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception{
        mockMvc=standaloneSetup(userController).build();
    }

    @Test
    @Rollback(value = true)
    public void userAdd() throws Exception{
        UserSignUp userSignUp = new UserSignUp();
        userSignUp.setBirth("19930930");
        userSignUp.setEmail("sjaq3jwor1@gmail.com");
        userSignUp.setPhonenumber("0130629216120");
        userSignUp.setName("이승기");
        userSignUp.setPassword("dltmdrl123");

        String json = this.json(userSignUp);

        MvcResult mvcResult = mockMvc.perform(post("/api/user/sign/up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void userLogin() throws Exception{
        UserLoginRequest login = new UserLoginRequest();
        login.setEmail("sjaqjwor1@gmail.com");
        login.setPassword("dltmdrl123");
        String json = this.json(login);
        MvcResult mvcResult = mockMvc.perform(post("/api/user/login").
                contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void userLoginFailed() throws Exception{
        UserLoginRequest login = new UserLoginRequest();
        login.setEmail("sjaqjwor1@gmail.com");
        login.setPassword("dltmdasdl123");
        String json = this.json(login);
        MvcResult mvcResult = mockMvc.perform(post("/api/user/login").
                contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void userFind() throws Exception {
        String token = token().split(",")[1];
        token=token.substring(10,token.length()-2);
        MvcResult mvcResult = mockMvc.perform(get("/api/user/find").header(
                "Authorization",token
        )).andDo(print()).andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    @Rollback(value = true)
    public void addInterest() throws Exception{
        String interest = "test";
        String token = token().split(",")[1];
        token=token.substring(10,token.length()-2);
        MvcResult mvcResult = mockMvc.perform(put("/api/user/add/interest").header(
                "Authorization",token
        ).param("interest",interest)).andDo(print()).andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

    }
//    @Test
//    public void addProfilImage() throws Exception{
//        String interest = "test";
//        String token = token().split(",")[1];
//        token=token.substring(10,token.length()-2);
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("profilImage","example.jpeg",MediaType.MULTIPART_FORM_DATA_VALUE,"/home/seungki/example.jpeg".getBytes());
//        System.err.println(mockMultipartFile.getOriginalFilename());
//        MockMultipartHttpServletRequestBuilder builders = MockMvcRequestBuilders.fileUpload("/user/add/profilimage");
//        builders.with(new RequestPostProcessor() {
//            @Override
//            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockHttpServletRequest) {
//                mockHttpServletRequest.setMethod("PUT");
//                return mockHttpServletRequest;
//            }
//        });
//
//        MvcResult mvcResult=mockMvc.perform(builders.file(mockMultipartFile
//       ).header("Authorization",token)).andDo(print()).andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//    }

    private String json(Object object) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


    public String token() throws Exception{
        UserLoginRequest login = new UserLoginRequest();
        login.setEmail("sjaqjwor1@gmail.com");
        login.setPassword("dltmdrl123");
        String json = this.json(login);

        MvcResult mvcResult = mockMvc.perform(post("/api/user/login").
                contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        return token;
    }


}
