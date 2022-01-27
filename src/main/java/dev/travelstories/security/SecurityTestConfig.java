package dev.travelstories.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("test")
@Configuration
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

   /*
    * https://www.baeldung.com/spring-security-disable-profile
    * voor in het verslag
    * */
   @Override
   public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/**");
   }
}
