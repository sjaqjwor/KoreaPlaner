package wooklee.koreaplaner.configs.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.dtos.user.FindUserDto;

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
    @Value("${jwt.issuer}")
    private String issuer;


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

    public String createToken(FindUserDto findUserDto){
        Map<String,Object> map  = new HashMap<>();
        map=generateClamis(findUserDto);
        return generateToken(map,findUserDto);
    }

    private String generateToken(Map<String,Object> map ,FindUserDto user){
        Date create = new Date();
        Date expir = new Date(create.getTime()+Long.valueOf(expiration)*1000);

        return Jwts.builder()
                .setClaims(map)
                .setSubject(user.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(create)
                .setExpiration(expir)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

// security
//    public boolean cofirmToken(String token, UserDetails userDetails){
//        JwtUser jwtUser = (JwtUser)userDetails;
//        String email = getEmailFromToken(token);
//        System.err.println(email);
//        return email.equals(jwtUser.getUsername())
//                &&!checkExpir(token);
//    }

    public boolean checkExpir(String token){
        Date ex = getExpriationDate(token);
        return ex.before(new Date());
    }

    public Date getExpriationDate(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public Map<String , Object> generateClamis(FindUserDto findUserDto){
        Map<String,Object> map = new HashMap<>();
        map.put("email",findUserDto.getEmail());
        return map;
    }


}
