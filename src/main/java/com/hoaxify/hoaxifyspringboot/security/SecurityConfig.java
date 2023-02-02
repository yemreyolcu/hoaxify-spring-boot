package com.hoaxify.hoaxifyspringboot.security;

import com.hoaxify.hoaxifyspringboot.security.jwt.service.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsService userDetailService;
    final JwtFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailService, JwtFilter jwtFilter) {
        this.userDetailService = userDetailService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/users/create")
                .permitAll()
                .antMatchers(AUTH_ACCESS_LIST)
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    private static final String[] AUTH_ACCESS_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/csrf/**"
    };
}
