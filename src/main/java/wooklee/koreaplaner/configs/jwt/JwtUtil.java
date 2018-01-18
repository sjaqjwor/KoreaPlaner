package wooklee.koreaplaner.configs.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;


    public String getEmailFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);

    }
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsTFunction){
        Claims claims = getClaimsFromToken(token);
        if(claims==null){
            return null;
        }else {
            return claimsTFunction.apply(claims);
        }
    }

    private Claims getClaimsFromToken(String token){
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){

        }
        return claims;
    }

    public String createToken(UserDetails userDetails){
        Map<String,Object> map  = new HashMap<>();
        return generateToken(map,userDetails);
    }

    private String generateToken(Map<String,Object> map ,UserDetails userDetails){
        Date create = new Date();
        Date expir = new Date(create.getTime()+Long.valueOf(expiration)*1000);

        return Jwts.builder()
                .setClaims(map)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(create)
                .setExpiration(expir)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public boolean cofirmToken(String token, UserDetails userDetails){
        JwtUser jwtUser = (JwtUser)userDetails;
        String email = getEmailFromToken(token);
        System.err.println(email);
        return email.equals(jwtUser.getUsername())
                &&!checkExpir(token);


    }

    private boolean checkExpir(String token){
        Date ex = getExpriationDate(token);
        return ex.before(new Date());
    }

    public Date getExpriationDate(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public Date getCreateDate(String token){
        return getClaimFromToken(token,Claims::getIssuedAt);
    }


}
