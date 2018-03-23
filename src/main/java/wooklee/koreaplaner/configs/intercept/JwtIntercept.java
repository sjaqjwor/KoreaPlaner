package wooklee.koreaplaner.configs.intercept;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wooklee.koreaplaner.configs.jwt.JwtUtil;
import wooklee.koreaplaner.utiles.ErrorStrings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtIntercept implements HandlerInterceptor{
    private Logger logger = LoggerFactory.getLogger(JwtIntercept.class);
    @Value("${jwt.header}")
   private String header;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String token = httpServletRequest.getHeader(header);
        if(token==null){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,ErrorStrings.TOKEN_IS_NOT_TRUE);
            return false;
        }

        String id = jwtUtil.getIdFromToken(token);
        if(id==null){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,ErrorStrings.TOKEN_IS_NOT_TRUE);
           return false;
        }
        if(jwtUtil.checkExpir(token)){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,ErrorStrings.FINISH_TOKEN_EXPIRE);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
