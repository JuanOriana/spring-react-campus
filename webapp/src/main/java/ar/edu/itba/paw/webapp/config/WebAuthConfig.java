package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/login").anonymous()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                    .loginPage("/login")
                .and().rememberMe()
                    .rememberMeParameter("rememberMe")
                    //.userDetailsService(userDetailsService)
                    .key("mysupersecretketthatnobodyknowsabout") // no hacer esto, crear una aleatoria segura suficiente mente grande y colocarla bajo src/main/resources
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
                .antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }

}