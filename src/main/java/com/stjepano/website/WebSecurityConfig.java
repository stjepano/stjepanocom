package com.stjepano.website;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web Security configuration.
 *
 * Restricts access to /admin/ section, allows access to public sections and login/logout forms
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll()
                .antMatchers("/admin", "/admin/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/admin/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/admin/logout")
                .permitAll();
    }


}
