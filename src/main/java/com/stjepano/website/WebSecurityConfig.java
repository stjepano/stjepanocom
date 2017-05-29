package com.stjepano.website;

import com.stjepano.website.modules.users.model.PersistentLogin;
import com.stjepano.website.modules.users.repos.PersistentLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Date;
import java.util.List;

/**
 * Web Security configuration.
 *
 * Restricts access to /admin/ section, allows access to public sections and login/logout forms
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentLoginRepository persistentLoginRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/admin/**");

        http.authorizeRequests()
            .antMatchers("/admin/", "/admin/**").authenticated()
            .and()
            .formLogin().successHandler(savedRequestAwareAuthenticationSuccessHandler()).loginPage("/admin/login").failureUrl("/admin/login?error").permitAll()
            .and()
            .rememberMe().tokenRepository(persistentTokenRepository())
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")).logoutSuccessUrl("/");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new PersistentTokenRepository() {
            @Override
            public void createNewToken(PersistentRememberMeToken token) {
                PersistentLogin persistentLogin = new PersistentLogin();
                persistentLogin.setUsername(token.getUsername());
                persistentLogin.setSeries(token.getSeries());
                persistentLogin.setToken(token.getTokenValue());
                persistentLogin.setLastUsed(token.getDate());

                persistentLoginRepository.save(persistentLogin);
                persistentLoginRepository.flush();
            }

            @Override
            public void updateToken(String series, String tokenValue, Date date) {
                PersistentLogin persistentLogin = persistentLoginRepository.findOne(series);
                persistentLogin.setLastUsed(date);
                persistentLogin.setToken(tokenValue);
                persistentLoginRepository.save(persistentLogin);
                persistentLoginRepository.flush();
            }

            @Override
            public PersistentRememberMeToken getTokenForSeries(String seriesId) {
                PersistentLogin persistentLogin = persistentLoginRepository.findOne(seriesId);
                PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLastUsed());
                return persistentRememberMeToken;
            }

            @Override
            public void removeUserTokens(String username) {
                List<PersistentLogin> persistentLogins = persistentLoginRepository.findByUsername(username);
                persistentLoginRepository.delete(persistentLogins);
                persistentLoginRepository.flush();
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


}
