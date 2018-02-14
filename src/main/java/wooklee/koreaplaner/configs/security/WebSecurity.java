package wooklee.koreaplaner.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import wooklee.koreaplaner.configs.cors.CorsFilter;
import wooklee.koreaplaner.configs.intercept.JwtIntercept;

import java.nio.charset.Charset;
import java.util.List;

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
        registry.addInterceptor(jwtIntercept)
//                .addPathPatterns("/api/users/*")
//                .addPathPatterns("/api/schedules/**")
                .excludePathPatterns("/api/users/sign/up")
                .excludePathPatterns("/api/users/login")
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
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){

        Charset standard = Charset.forName("UTF-8");

        MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
        jackson.setDefaultCharset(standard);

        ResourceHttpMessageConverter resource = new ResourceHttpMessageConverter();
        converters.add(jackson);
        converters.add(resource);
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
