package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.CampusUserDetailsService;
import ar.edu.itba.paw.webapp.auth.CourseVoter;
import ar.edu.itba.paw.webapp.util.KeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CampusUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public CourseVoter courseVoter() { return new CourseVoter(); }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        http.sessionManagement()
                    .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/login").anonymous()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    /*.antMatchers(HttpMethod.GET, "/files/{fileId}").access("@courseVoter.hasFileAccess(authentication,#fileId)")
                    .antMatchers(HttpMethod.POST, "/course/{courseId}/announcements").access("@courseVoter.hasCoursePrivileges(authentication,#courseId)")
                    .antMatchers(HttpMethod.POST, "/course/{courseId}/files").access("@courseVoter.hasCoursePrivileges(authentication,#courseId)")
                    .antMatchers(HttpMethod.GET, "/course/{courseId}/**").access("@courseVoter.hasCourseAccess(authentication,#courseId)")*/
                    .antMatchers("/portal").hasAuthority("USER")
                    .antMatchers("/announcements").hasAuthority("USER")
                    .antMatchers("/timetable").hasAuthority("USER")
                    .antMatchers("/files").hasAuthority("USER")
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                    .loginPage("/login")
                .and().rememberMe()
                    .rememberMeParameter("rememberMe")
                    .userDetailsService(userDetailsService)
                    .key(KeyReader.get("/remember_me_key.secure"))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/css/**", "/resources/js/**", "/resources/images/**", "/403", "/404");
    }



}