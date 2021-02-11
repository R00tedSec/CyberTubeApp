package com.cybertube.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.authorizeRequests().antMatchers("/deleteArticle/**").authenticated();
        http.authorizeRequests().antMatchers("/article/deleteArticle/**").authenticated();
        http.authorizeRequests().antMatchers("/video/deleteVideo/**").authenticated();
        http.authorizeRequests().antMatchers("/article/updateArticle/**").authenticated();
        http.authorizeRequests().antMatchers("/video/updateVideo/**").authenticated();
        http.authorizeRequests().antMatchers("/postVideo").authenticated();
        http.authorizeRequests().antMatchers("/postArticle").authenticated();

        http.authorizeRequests().antMatchers("/help").permitAll();
        http.authorizeRequests().antMatchers("/ckeditor/**").permitAll();
        http.authorizeRequests().antMatchers("/videos/**").permitAll();
        http.authorizeRequests().antMatchers("/video/**").permitAll();
        http.authorizeRequests().antMatchers("/articles/**").permitAll();
        http.authorizeRequests().antMatchers("/article/**").permitAll();
        http.authorizeRequests().antMatchers("/css/**").permitAll();
        http.authorizeRequests().antMatchers("/bootstrap/**").permitAll();
        http.authorizeRequests().antMatchers("/fonts/**").permitAll();
        http.authorizeRequests().antMatchers("/files/**").permitAll();
        http.authorizeRequests().antMatchers("/js/**").permitAll();
        http.authorizeRequests().antMatchers("/img/**").permitAll();
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/register/newuser").permitAll();
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/exit").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/index").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();

        http.exceptionHandling().accessDeniedPage("/error.html");

        // Anonymous user can navigate our web and search
        http.authorizeRequests().antMatchers("/**").authenticated();

        // Public pages for any user logged
        http.authorizeRequests().anyRequest().authenticated();

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        // http.formLogin().successForwardUrl("/home");
        http.formLogin().failureUrl("/loginerror");

        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        // Disable CSRF at the moment
        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

}
