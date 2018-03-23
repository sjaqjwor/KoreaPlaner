//package wooklee.koreaplaner;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import wooklee.koreaplaner.controllers.FriendController;
//import wooklee.koreaplaner.controllers.requests.friend.AcceptFriendRequest;
//import wooklee.koreaplaner.controllers.requests.friend.AddFriendRequest;
//import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class FriendControllerTest {
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FriendControllerTest.class);
//
//    @Autowired
//    private WebApplicationContext ctx;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() throws Exception{
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
//    }
//
//    @Test
//    public void findFriend() throws Exception{
//        String token = token();
//        MvcResult mvcResult = mockMvc.perform(get("/api/friends/{name}","노").header("Authorization",token).
//                contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    public void getFriends() throws Exception{
//        String token = token();
//        MvcResult mvcResult = mockMvc.perform(get("/api/{uid}/friends","46").header("Authorization",token).
//                contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    public void getreceiveFriends() throws Exception{
//        String token = token();
//        MvcResult mvcResult = mockMvc.perform(get("/api/{uid}/friends/receive","46").header("Authorization",token).
//                contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//
//    @Test
//    public void addFriend() throws Exception{
//        String token = token();
//        AddFriendRequest afr = new AddFriendRequest();
//        afr.setFidx(new Long(46));
//        afr.setUidx(new Long(43));
//        MvcResult mvcResult = mockMvc.perform(post("/api/friend/add").header("Authorization",token).content(json(afr)).
//                contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    public void acceptFriend() throws Exception{
//        String token = token();
//        AcceptFriendRequest afr = new AcceptFriendRequest();
//        afr.setFid(new Long(45));
//        afr.setUid(new Long(46));
//        afr.setStatus(new Long(1));
//        MvcResult mvcResult = mockMvc.perform(put("/api/friend/accept").header("Authorization",token).content(json(afr)).
//                contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//
//    public String token() throws Exception{
//        UserLoginRequest login = new UserLoginRequest();
//        login.setEmail("2");
//        login.setPassword("2");
//        String json = this.json(login);
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/login").
//                contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andReturn();
//        String token = mvcResult.getResponse().getContentAsString();
//        return token.split(",")[0].split(":")[1].replace("\"","");
//    }
//    private String json(Object object) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(object);
//    }
//}
