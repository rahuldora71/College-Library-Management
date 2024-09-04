package com.pw.skills.clm.security_configuration.college_admin;

import com.pw.skills.clm.security_configuration.CustomAccessDeniedHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity

public class CollegeSecurityConfig {
    @Bean
     public UserDetailsService collegeUserDetailsService() {
        return new CollegeDetailsServiceImpl();
    }
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

//    @Bean
//    public AuthenticationManager authenticationManager1(AuthenticationConfiguration builder) throws Exception {
//        return builder.getAuthenticationManager();
//    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider1(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(collegeUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder1());
        return daoAuthenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http.authenticationProvider(daoAuthenticationProvider1());

      http.authorizeHttpRequests(authorize -> {
          authorize.requestMatchers("/college/**").hasAuthority("COLLEGE");
          authorize.requestMatchers("/librarian/**").hasAuthority("LIBRARIAN");
          authorize.requestMatchers("/**").permitAll();




      });
        http.formLogin(formLogin-> {
            formLogin.loginPage("/login");

            formLogin.failureUrl("/login?error");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.successHandler((request, response, authentication) -> {
                // Redirection logic based on role
                String role = authentication.getAuthorities().iterator().next().getAuthority();
                if ("COLLEGE".equals(role)) {
                    response.sendRedirect("/college/dashboard");
                } else if ("LIBRARIAN".equals(role)) {
                    response.sendRedirect("/librarian/home");
                }
            });


            formLogin.permitAll();
        });
//        http.addFilter(new RedirectLoginFilter());



        http.csrf(csrf->{
            csrf.disable();
        });
        http.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.permitAll();
        });

        http.exceptionHandling(handler ->
                handler.accessDeniedHandler(accessDeniedHandler));

        http.addFilterBefore(new HttpFilter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                HttpServletRequest request1 = (HttpServletRequest) request;
                // If the user is authenticated and tries to access the login page
                    HttpServletResponse response1= (HttpServletResponse) response;
                if (authentication != null && authentication.isAuthenticated() &&
                        new AntPathRequestMatcher("/login").matches(request1)) {

                    // Redirect based on role
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    System.out.println(role);
                    if ("COLLEGE".equals(role)) {
                        response1.sendRedirect("/college/dashboard");
                    } else if ("LIBRARIAN".equals(role)) {
                        response1.sendRedirect("/librarian/dashboard");
                    }

            }
                chain.doFilter(request1, response1);
        }}, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
