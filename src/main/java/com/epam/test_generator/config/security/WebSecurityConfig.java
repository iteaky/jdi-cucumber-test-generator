package com.epam.test_generator.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jwtAuthenticationFilter
            .setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(
            new JwtAuthenticationFailureHandler());
        return jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * sessionCreationPolicy.STATELESS means that wee don't save cookies.
     *
     * We do not need csrf protection because our tokens are immune to it.
     *
     * Here we can configurate our security settings. We can give a permission fo all or any users
     * to visit some resources.
     *
     * We plug in our special authentication filter within the Spring’s predefined filter chain,
     * just before the form login filter.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/registration", "/login")
            .permitAll()
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    /**
     * Here we exclude some resources from all filters.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login", "/registration","/v2/api-docs",
            "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html",
            "/webjars/**");
    }

}