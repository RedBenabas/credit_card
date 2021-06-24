package com.publicis.creditcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("reader")
                .password(encoder.encode("reader"))
                .roles("VIEW");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("writer")
                .password(encoder.encode("writer"))
                .roles("VIEW","EDIT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/credit_cards/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/credit_cards/**").hasRole("VIEW")
                .antMatchers(HttpMethod.POST, "/credit_cards/**").hasRole("EDIT")
                .anyRequest().authenticated().and().httpBasic();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
