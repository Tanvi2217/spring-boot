package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class ProductSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector mappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(mappingIntrospector);
        httpSecurity.csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg").ignoringRequestMatchers(PathRequest.toH2Console()))
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/displayMessages")).hasRole("ADMIN")
                                .requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/closeMsg/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll())
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login").defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());
        httpSecurity.headers(headersConfigurer -> headersConfigurer
                .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        return httpSecurity.build();
    }

    //storing user details in memory
    @Bean
    public InMemoryUserDetailsManager userDetailsSerice() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234567")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }
}
