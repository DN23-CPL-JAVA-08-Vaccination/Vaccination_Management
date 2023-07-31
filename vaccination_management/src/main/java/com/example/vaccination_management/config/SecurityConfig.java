package com.example.vaccination_management.config;

import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountDetailService accountService;

    @Autowired
    LoginSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/img/**", "/css/**", "/vendor/**", "/js/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/employee/**").access("hasRole('ROLE_EMPLOYEE')")
                .antMatchers("/infor/**").access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(successHandler)
                .failureUrl("/login?success=fail")
                .loginProcessingUrl("/j_spring_security_check")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("remember-me")
                .and().rememberMe().key("remember-me").tokenValiditySeconds(86400);
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }
}
