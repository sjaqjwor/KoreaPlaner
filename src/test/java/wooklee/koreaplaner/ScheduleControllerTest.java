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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleListRequest;
import wooklee.koreaplaner.controllers.requests.schedule.DetailScheduleRequest;
import wooklee.koreaplaner.controllers.requests.schedule.ScheduleRequest;
import wooklee.koreaplaner.controllers.requests.user.UserLoginRequest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class ScheduleControllerTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduleControllerTest.class);

    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext ctx;



    @Before
    public void setUp() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void createSchedule() throws Exception{
        String interest = "test";
        String token = token().split(",")[1];
        token=token.substring(10,token.length()-2);

        ScheduleRequest createSchedule = new ScheduleRequest();
        createSchedule.setEnddate("20180203");
        createSchedule.setStartdate("20180129");
        createSchedule.setTitle("국내일주");
        createSchedule.setThema("신혼일까");

        MvcResult mvcResult = mockMvc.perform(post("/api/schedules/schedule").header(
                "Authorization",token
        ).contentType(MediaType.APPLICATION_JSON)
                .content(json(createSchedule)))
                .andExpect(status().isOk()).andDo(print()).andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());


    }
    @Test
    public void createDetailSchedule() throws Exception{
        String token = token().split(",")[1];
        token=token.substring(10,token.length()-2);
        DetailScheduleRequest d = new DetailScheduleRequest();
        d.setCity("서울");
        d.setEnddate("20180129");
        d.setStartdate("20180130");
        d.setLatitude(12.33333);
        d.setLongitude(15.666);
        d.setTraffic("지하철");
        DetailScheduleRequest d1 = new DetailScheduleRequest();
        d1.setCity("전주");
        d1.setEnddate("20180130");
        d1.setStartdate("20180131");
        d1.setLatitude(15.5555);
        d1.setLongitude(16.55555);
        d1.setTraffic("택시");
        DetailScheduleRequest d2 = new DetailScheduleRequest();
        d2.setCity("부산");
        d2.setEnddate("20180131");
        d2.setStartdate("20180132");
        d2.setLatitude(17.45454);
        d2.setLongitude(18.45454);
        d2.setTraffic("버스");
        List<DetailScheduleRequest> list = new ArrayList<>();
        list.add(d);
        list.add(d1);
        list.add(d2);
        DetailScheduleListRequest detailScheduleListRequest = new DetailScheduleListRequest();
        detailScheduleListRequest.setList(list);
        System.err.println(json(detailScheduleListRequest));
        MvcResult mvcResult = mockMvc.perform(post("/api/schedules/{idx}/detail","7").header(
                "Authorization",token
        ).contentType(MediaType.APPLICATION_JSON).content(json(detailScheduleListRequest))).andDo(print()).andExpect(status().isOk()).andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void updateSchedule() throws Exception{
        String token = token().split(",")[1];
        token=token.substring(10,token.length()-2);
        ScheduleRequest sr = new ScheduleRequest();
        sr.setEnddate("20180203");
        sr.setStartdate("20180129");
        sr.setTitle("국내일주실어");
        sr.setThema("신혼아니다");

        MvcResult mvcResult = mockMvc.perform(put("/api/schedules/{idx}",10).header(
                "Authorization",token
        ).contentType(MediaType.APPLICATION_JSON).content(
                json(sr)
        )).andExpect(status().isOk()).andDo(print()).andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

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

    private String json(Object object) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
