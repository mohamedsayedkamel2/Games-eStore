package com.store.videogames.config.security;

import com.store.videogames.config.security.oAuth2.CustomAuth2UserService;
import com.store.videogames.config.security.oAuth2.OAuth2SuccessfultHandler;
import com.store.videogames.util.common.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    DataSource dataSource;
    @Autowired
    CustomAuth2UserService auth2UserService;
    @Autowired
    OAuth2SuccessfultHandler oAuth2SuccessfultHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //This function will return a Bean Object which will help to enable login using Spring Data JPA
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsServiceImpl();
    }

    //This function is used to enable login using Spring Data JPA
    @Bean
    DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder.getBcryptPasswordEncoder());
        return authenticationProvider;
    }

    //This function is used to enable RememberMe functionality
    @Bean
    PersistentTokenRepository persistentTokenRepository()
    {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    //This function is used to enable login using Spring Data JPA
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity webSecurity)
    {
        webSecurity.ignoring().
                antMatchers("/customer/register").
                antMatchers("/forgot_password").
                antMatchers("/reset_password").
                antMatchers("/verify").
                antMatchers("/verify/*").
                antMatchers("/oauth2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/admin/customers/**").hasAuthority("ADMIN").
                antMatchers("/logout", "/videogames/buy/**", "/customer/**").hasAuthority("USER").
                and().exceptionHandling()
                .accessDeniedPage("/404");

                http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().permitAll().loginPage("/login").permitAll().
                and().logout().permitAll().
                and().rememberMe().key("123456789abcdefghijklmnopqrdtuzwxyZ").
                        tokenValiditySeconds(30 * 24 * 60 * 60).
        //This part is concerned about Google Login configuration
                and().oauth2Login().loginPage("/login").userInfoEndpoint().userService(auth2UserService).
                and().successHandler(oAuth2SuccessfultHandler);
    }
}
