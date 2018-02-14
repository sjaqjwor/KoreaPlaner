//package wooklee.koreaplaner;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//
//
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.request.RequestPostProcessor;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//import wooklee.koreaplaner.controllers.UserController;
//import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
//import wooklee.koreaplaner.controllers.requests.user.UserRequest;
//
//import wooklee.koreaplaner.controllers.responses.DefaultResponse;
//import wooklee.koreaplaner.services.UserService;
//
//
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
////@Transactional
//public class UserControllerTest {
//
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
//
//    private MockMvc mockMvc;
//
//
////    @Autowired
////    private UserController userController;
////
////    @Autowired
////    private UserService userService;
////
////    @Before
////    public void setUp() throws Exception{
////        mockMvc=standaloneSetup(userController).build();
////    }
//@Autowired
//private WebApplicationContext ctx;
//
//    @Before
//    public void setUp() throws Exception{
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
//    }
//
//    @Test
//    public void userAdd() throws Exception{
//        UserRequest userSignUp = new UserRequest();
//        userSignUp.setBirth("19930930");
//        userSignUp.setEmail("sjaq3jwor1@gmail.com");
//        userSignUp.setPhonenumber("0130629216120");
//        userSignUp.setName("이승기");
//        userSignUp.setPassword("dltmdrl123");
//
//        String json = this.json(userSignUp);
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/sign/up")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//
//    @Test
//    public void userLogin() throws Exception{
//        UserLoginRequest login = new UserLoginRequest();
//        login.setEmail("sjaq3jwor1@gmail.com");
//        login.setPassword("dltmdrl123");
//        String json = this.json(login);
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/login").
//                contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//    }
//
//    @Test
//    public void userLoginFailed() throws Exception{
//        UserLoginRequest login = new UserLoginRequest();
//        login.setEmail("sjaqjwor1@gmail.com");
//        login.setPassword("dltmdasdl123");
//        String json = this.json(login);
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/login").
//                contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//
//    @Test
//    public void userFind() throws Exception {
//        String token = token();
//        System.err.println(token);
//        MvcResult mvcResult = mockMvc.perform(get("/api/users/{idx}",43).header(
//                "Authorization",token
//        )).andExpect(status().isOk()).andDo(print()).andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//    }
//
//    @Test
//    public void updateUser() throws Exception {
//        String token = token();
//        UserRequest userUpdate = new UserRequest();
//        userUpdate.setName("kk1111123123w");
//        userUpdate.setInterest("gksrn11111r");
//        userUpdate.setPassword("123");
//        userUpdate.setEmail("sjaq3jwor1@gmail.com");
//        userUpdate.setPhonenumber("01091476976");
//        MvcResult mvcResult = mockMvc.perform(put("/api/users/{idx}",43).header(
//                "Authorization",token
//        ).contentType(MediaType.APPLICATION_JSON).content(json(userUpdate))).andExpect(status().isOk()).andDo(print()).andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//    }
//
//
////    @Test
////    @Rollback(value = true)
////    public void addInterest() throws Exception{
////        String interest = "testasdasdasd";
////        String token = token().split(",")[1].split(":")[1];
////        token=token.replace("[","").replace("\"","");
////        MvcResult mvcResult = mockMvc.perform(put("/api/users/{idx}/interest",26).header(
////                "Authorization",token
////        ).param("interest",interest)).andExpect(status().isOk()).andDo(print()).andReturn();
////        logger.info(mvcResult.getResponse().getContentAsString());
////
////    }
////    @Test
////    public void addProfilImage() throws Exception{
////        String interest = "test";
////        String token = token().split(",")[1].split(":")[1];
////        token=token.replace("[","").replace("\"","");
////        MockMultipartFile mockMultipartFile = new MockMultipartFile("profilImage","example.jpeg",MediaType.MULTIPART_FORM_DATA_VALUE,"/home/seungki/example.jpeg".getBytes());
////        System.err.println(mockMultipartFile.getOriginalFilename());
////        MockMultipartHttpServletRequestBuilder builders = MockMvcRequestBuilders.fileUpload("/api/users/{idx}/profilimage",38);
////        builders.with(new RequestPostProcessor() {
////            @Override
////            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockHttpServletRequest) {
////                mockHttpServletRequest.setMethod("PUT");
////                return mockHttpServletRequest;
////            }
////        });
////
////        MvcResult mvcResult=mockMvc.perform(builders.file(mockMultipartFile
////       ).header("Authorization",token)).andDo(print()).andExpect(status().isOk()).andReturn();
////        logger.info(mvcResult.getResponse().getContentAsString());
////    }
//
//    private String json(Object object) throws JsonProcessingException{
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(object);
//    }
//
//
//    public String token() throws Exception{
//        UserLoginRequest login = new UserLoginRequest();
//        login.setEmail("sjaq3jwor1@gmail.com");
//        login.setPassword("123");
//        String json = this.json(login);
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/login").
//                contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andReturn();
//        String token = mvcResult.getResponse().getContentAsString();
//        return token.split("}")[1].split(":")[1].replace("\"","");
//    }
//
//
//}
