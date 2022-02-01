package ar.edu.itba.paw.webapp.security.config;

import ar.edu.itba.paw.webapp.security.api.*;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationProvider;
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
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.session.SessionManagementFilter;
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
    public CORSFilter corsFilter() {
        return new CORSFilter();
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
            .addFilterBefore(corsFilter(), SessionManagementFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/announcements").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET,"/timetable").hasAuthority("USER")
                    .antMatchers(HttpMethod.GET,"/files").hasAuthority("USER")
                    .antMatchers("/users").hasAuthority("ADMIN")
                    .antMatchers( "/courses").hasAuthority("ADMIN")
                    .antMatchers("/**").permitAll()
                .accessDecisionManager(accessDecisionManager())
            .and()
                .addFilterBefore(bridgeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) {
        web
           .ignoring()
                .antMatchers("/");
    }



}