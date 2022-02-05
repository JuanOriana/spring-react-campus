package ar.edu.itba.paw.webapp.security.config;

import ar.edu.itba.paw.webapp.security.api.*;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.voter.AntMatcherVoter;
import ar.edu.itba.paw.webapp.security.voter.CampusVoter;
import ar.edu.itba.paw.webapp.security.service.implementation.CampusUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.security" })
@PropertySource(value= {"classpath:application.properties"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CampusUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(basicAuthenticationProvider).authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BridgeAuthenticationFilter bridgeAuthenticationFilter() throws Exception {
        BridgeAuthenticationFilter bridgeAuthenticationFilter = new BridgeAuthenticationFilter();
        bridgeAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        bridgeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        bridgeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return bridgeAuthenticationFilter;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                courseVoter());
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public CampusVoter courseVoter() { return new CampusVoter(); }

    @Bean
    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http
            .cors()
                .and()
            .csrf()
                .disable()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/announcements").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET, "/announcements/{announcementId}").access("@antMatcherVoter.canAccessAnnouncementById(authentication, #announcementId)")
                    .antMatchers(HttpMethod.GET, "/answers/{answerId}").access("@antMatcherVoter.canAccessAnswerById(authentication, #answerId)")
                    .antMatchers(HttpMethod.GET, "/courses").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/courses/available-years").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/files").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/announcements").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/teachers").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/helpers").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/students").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/exams").access("@antMatcherVoter.isPrivilegedInCourse(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/exams/solved").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/exams/unsolved").access("@antMatcherVoter.canAccessCourseById(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/exams/answers").access("@antMatcherVoter.isPrivilegedInCourse(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET, "/courses/{courseId}/exams/average").access("@antMatcherVoter.isPrivilegedInCourse(authentication, #courseId)")
                    .antMatchers(HttpMethod.GET,"/timetable").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET,"/files").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET,"/files/{fileId}").access("@antMatcherVoter.canAccessFileById(authentication, #fileId)")
                    .antMatchers(HttpMethod.GET, "/user").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET, "/users/{userId}/**").access("@antMatcherVoter.canAccessUserById(authentication, #userId)")
                    .antMatchers(HttpMethod.POST, "/courses/{courseId}/exams").access("@antMatcherVoter.isPrivilegedInCourse(authentication, #courseId)")
                    .antMatchers(HttpMethod.POST, "/courses/{courseId}/files").access("@antMatcherVoter.isPrivilegedInCourse(authentication, #courseId)")
                    .antMatchers(HttpMethod.POST, "/courses").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.POST, "/courses/{courseId}/announcements").access("@antMatcherVoter.canPostAnnouncementByCourseId(authentication, #courseId)")
                    .antMatchers(HttpMethod.DELETE,"/files/{fileId}").access("@antMatcherVoter.canDeleteFileById(authentication, #fileId)")
                    .antMatchers(HttpMethod.DELETE, "/answers/{answerId}").access("@antMatcherVoter.canDeleteAnswerById(authentication, #answerId)")
                    .antMatchers(HttpMethod.DELETE, "/announcements/{announcementId}").access("@antMatcherVoter.canDeleteAnnouncementById(authentication, #announcementId)")
                    .antMatchers("/users").hasAuthority("ADMIN")
                    .antMatchers("/users/file-number/last").hasAuthority("ADMIN")
                    .antMatchers("/subjects/**").hasAuthority("USER")
                    .antMatchers("/user").hasAuthority("USER")
                    .antMatchers("/timetable").hasAuthority("USER")
                    .antMatchers("/**").permitAll()
                //.accessDecisionManager(accessDecisionManager())
            .and()
                .addFilterBefore(bridgeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(final WebSecurity web) {
        web
           .ignoring()
                .antMatchers("/");
    }



}