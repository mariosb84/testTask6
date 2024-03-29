package com.example.itemservice.security;

import com.example.itemservice.filter.JWTAuthenticationFilter;
import com.example.itemservice.filter.JWTAuthorizationFilter;
import com.example.itemservice.service.UserServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

/*import static com.example.itemservice.filter.JWTAuthenticationFilter.SIGN_UP_URL;*/


/*@EnableWebSecurity*/
public class WebSecurity /*extends WebSecurityConfigurerAdapter*/ {

 /*   @Autowired
    DataSource ds;
    private final UserServiceData userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserServiceData userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                 http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                         .antMatchers("/items/findAll").hasAnyRole("Администратор", "Пользователь", "Оператор")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))

                .formLogin()
                         .permitAll()
                .and()
                .logout()
                         .permitAll()
                .and()

                *//* this disables session creation on Spring Security *//*
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

       *//* auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery("select person_login, person_password, person_phone "
                        + "from person "
                        + "where person_login = ?")
                .authoritiesByUsernameQuery(
                        " select p.person_login, r.role_name "
                                + "from  person as p,  person_role as r"
                        + "JOIN accidentRules r ON r.accidentRule_id IN (SELECT rules_id FROM link WHERE accidentLink_id = a.accident_id) "
                                + "where u.user_name = ? and u.authority_id = a.authorities_id");*//*

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }*/

}