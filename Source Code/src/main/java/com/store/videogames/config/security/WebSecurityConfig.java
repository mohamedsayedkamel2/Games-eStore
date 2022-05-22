package com.store.videogames.config.security;

import com.store.videogames.config.security.oAuth2.CustomAuth2UserService;
import com.store.videogames.config.security.oAuth2.OAuth2SuccessfultHandler;
import com.store.videogames.util.common.PasswordEncoder;
import com.store.videogames.repository.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    CustomerRepository customerRepository;
    @Autowired
    CustomAuth2UserService auth2UserService;
    @Autowired
    OAuth2SuccessfultHandler oAuth2SuccessfultHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsServiceImpl();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(PasswordEncoder.getBcryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository()
    {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().
                antMatchers("/login").permitAll().
                antMatchers("/customer/register").permitAll().
                antMatchers("/forgot_password").permitAll().
                antMatchers("/reset_password").permitAll().
                antMatchers("/verify").permitAll().
                antMatchers("/verify/*").permitAll().
                antMatchers("/oauth2/**").permitAll().
                anyRequest().authenticated()
                .and()
                .formLogin().permitAll().loginPage("/login").
                usernameParameter("username").passwordParameter("password")
                .permitAll().failureUrl("/login?error").defaultSuccessUrl("/").
                and().logout().logoutUrl("/logout").permitAll().
                and().rememberMe().tokenRepository(persistentTokenRepository()).
                and().oauth2Login().loginPage("/login").userInfoEndpoint().userService(auth2UserService).
                and().successHandler(oAuth2SuccessfultHandler);
    }
}
