package wooklee.koreaplaner.configs.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wooklee.koreaplaner.domains.User.User;

import java.util.Collection;

public class JwtUser implements UserDetails{

    private String email;
    private String username;
    private String password;


    public JwtUser(String emil,String username,String password){
        this.email=emil;
        this.username=username;
        this.password=password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
