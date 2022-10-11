package com.tworuszka.players.security;

import com.tworuszka.players.filter.CustomAuthenticationFilter;
import com.tworuszka.players.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Michał Tworuszka on 11.10.2022
 * @project dnd-training-project
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //private final UserDetailsService userDetailsService;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { //here we use when declared http.getSharedObject(AuthenticationConfiguration.class in place of AuthConfig
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));
        //filter.setFilterProcessesUrl("/player/login");

        //todo add all access matchers and authorities
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/player/refreshtoken/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/player/").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/player/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(filter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .antMatchers("/welcome").authenticated()
//                .antMatchers("/admin").hasAuthority("ADMIN")
//                .antMatchers("/emp").hasAuthority("EMPLOYEE")
//                .antMatchers("/mgr").hasAuthority("MANAGER")
        // .anyRequest().authenticated();
        return http.build();
    }


}
