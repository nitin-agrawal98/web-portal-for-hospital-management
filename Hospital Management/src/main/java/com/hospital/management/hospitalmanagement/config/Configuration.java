package com.hospital.management.hospitalmanagement.config;

import com.hospital.management.hospitalmanagement.jwt.AuthEntryPointJwt;
import com.hospital.management.hospitalmanagement.jwt.AuthTokenFilter;
import com.hospital.management.hospitalmanagement.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Configuration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.cors();
//        http.csrf().disable();
//        http.httpBasic().and()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests().antMatchers("/", "/home").permitAll().and()
//                .authorizeRequests().antMatchers("/patients/**").hasAnyRole("PATIENT", "ADMIN").and()
//                .authorizeRequests().antMatchers("/doctors/**").hasAnyRole("DOCTOR", "ADMIN").and()
//                .authorizeRequests().antMatchers("/appointments/id/**").authenticated().and()
//                .authorizeRequests().antMatchers("/appointments/patient/**").hasAnyRole("ADMIN", "PATIENT").and()
//                .authorizeRequests().antMatchers("/appointments/doctor/**").hasAnyRole("ADMIN", "DOCTOR").and()
//                .authorizeRequests().antMatchers("request/patient/**").hasAnyRole("ADMIN", "PATIENT").and()
//                .authorizeRequests().antMatchers("request/**").hasAnyRole("ADMIN").and()
//                .formLogin();
//
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
