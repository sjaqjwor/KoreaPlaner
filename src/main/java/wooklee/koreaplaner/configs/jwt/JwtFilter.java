//package wooklee.koreaplaner.configs.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//import wooklee.koreaplaner.utiles.ErrorStrings;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;


//public class JwtFilter extends OncePerRequestFilter{
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Value("${jwt.header}")
//    private String header;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String requestheader = httpServletRequest.getHeader(header);
//        String email=null;
//        String token = null;
//        if(requestheader != null){
//            token = requestheader;
//            try {
//                email = jwtUtil.getEmailFromToken(token);
//            }catch (ExpiredJwtException e){
//                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,ErrorStrings.FINISH_TOKEN_EXPIRE);
//            }
//        }else{
//            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,ErrorStrings.TOKEN_IS_NOT_TRUE);
//        }
//
//
//        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails =userDetailsService.loadUserByUsername(email);
//            if(jwtUtil.cofirmToken(token,userDetails)){
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }else{
//                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        }
//
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//}
