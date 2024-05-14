package com.app.config;



import com.app.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
      return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
          .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(http -> {
         //configurar endpoints publicos
       http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();
                   //configurar los endpoints privados
                   http.requestMatchers(HttpMethod.POST, "/auth/post").hasRole("ADMIN");
          http.requestMatchers(HttpMethod.POST, "/auth/patch").hasAnyAuthority("REFACTOR");

          //configurar el resto de endpoints no especificos
                   http.anyRequest().denyAll();
               })
               .build();

    }

    //@Bean
    //public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
      //return httpSecurity
        //    .csrf(csrf -> csrf.disable())
          //.httpBasic(Customizer.withDefaults())
        //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          //    .build();
     //}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (UserDetailServiceImpl userDetailService){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService  );
        return provider;
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
