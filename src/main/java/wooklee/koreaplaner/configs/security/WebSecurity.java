package wooklee.koreaplaner.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import wooklee.koreaplaner.configs.cors.CorsFilter;
import wooklee.koreaplaner.configs.intercept.JwtIntercept;

@Configuration
public class WebSecurity extends WebMvcConfigurerAdapter{

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public JwtFilter authenticationTokenFilterBean() throws Exception {
//        return new JwtFilter();
//    }

    @Autowired
    private JwtIntercept jwtIntercept;

    @Bean
    public CorsFilter corsFilterBean() throws Exception {
        return new CorsFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIntercept).addPathPatterns("/api/**").excludePathPatterns("/api/user/login")
                .excludePathPatterns("/static/**", "swagger-ui.html", "/webjars/**", "/v2/api-docs", "/configuration/security", "/configuration/ui", "/swagger-resources");
        ;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity
////
////                .csrf().disable()
//////                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
////                .authorizeRequests()
////                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////                .antMatchers("/**").permitAll()
////                .antMatchers(HttpMethod.GET).permitAll()
////                .anyRequest().authenticated();
////
////        httpSecurity
////                .addFilterBefore(corsFilterBean(),ChannelProcessingFilter.class)
////                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
////        httpSecurity.headers().cacheControl();
//    }
}
