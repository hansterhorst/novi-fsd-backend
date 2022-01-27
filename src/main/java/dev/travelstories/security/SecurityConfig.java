package dev.travelstories.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static dev.travelstories.constants.Constants.*;

@Configuration
@EnableWebSecurity
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private CustomUserDetailsService userDetailsService;

   @Autowired
   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

   @Bean
   public JwtAuthenticationFilter jwtAuthenticationFilter() {
      return new JwtAuthenticationFilter();
   }


   @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }


   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
              .csrf().disable().cors().and()
              .exceptionHandling()
              .authenticationEntryPoint(jwtAuthenticationEntryPoint)
              .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeRequests()
              .antMatchers(AUTHORITY_ADMIN_ACCESS_URL + "/**").hasAnyRole(ROLE_ADMIN)
              .antMatchers(AUTHORITY_USER_ACCESS_URL + "/**").hasAnyRole(ROLE_USER, ROLE_ADMIN)
              .antMatchers(HttpMethod.GET, PUBLIC_ACCESS_URL + "/**").permitAll()
              .antMatchers(HttpMethod.POST, AUTHENTICATION_ACCESS_URL + "/**").permitAll()
              .anyRequest()
              .authenticated();

      http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
   }


   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
   }


   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
}
