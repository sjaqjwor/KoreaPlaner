package wooklee.koreaplaner.configs.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper um;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = um.confirmUser(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not exist"));
        } else {
            JwtUser  jwtUser=new JwtUser(user.getEmail(), user.getName(), user.getPassword());
            return jwtUser;
        }
    }



}
