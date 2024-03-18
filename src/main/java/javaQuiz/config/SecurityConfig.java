package javaQuiz.config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaQuiz.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	System.out.println("Iz SecurityFilterChain");
    	
        http
            .authorizeRequests()
            	.requestMatchers("/admin/**").hasAuthority("ADMIN")
            	.requestMatchers("/user/**").hasAuthority("USER")
            	.requestMatchers("/public/**","/login*").permitAll()
            	.requestMatchers("/css/**").permitAll()
            	.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
            	.permitAll()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                    	String username=authentication.getName();
                    	userService.changeDate(username);
                    	String role = authentication.getAuthorities().toString();
                        System.out.println("Role usera je "+role);
                        if (role.contains("ADMIN")) {
                            response.sendRedirect("/admin/homeadmin");
                        } else if (role.contains("USER")) {
                            response.sendRedirect("/user/homeuser");
                        }
                    }
                })
                .permitAll()
                .and()
            .logout()
                .permitAll();
   
        return http.build();
    }
    /*
     * Spring MVC will perform a forward to the view login, 
     * which, with the default configuration, is under src/main/resources/templates/login.html path. 
     * The security configuration permits requests to /login but every other request will be denied, 
     * including the FORWARD request to the view under /templates/login.html.
     */
  

    @Bean
    public UserDetailsService userDetailsService() {
        return userService; // Assuming you have a custom UserDetailsService (UserService) to load user details from the database
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // For practice project, not recommended for production
    }
    /*
     * Spring Boot 2.x and earlier: Spring Security automatically created a DaoAuthenticationProvider bean if a UserDetailsService was present.
     *  This behavior changed in Spring Boot 3.x.
	 *	Solution: Explicitly define a DaoAuthenticationProvider bean in your configuration:
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    //ResourceHandler da pravilno služi staticke resurse
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }
}