package bg.softuni.hotelagency.config;

import bg.softuni.hotelagency.service.impl.DBTravellerUserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DBTravellerUserService dbTravellerUserService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(DBTravellerUserService dbTravellerUserService, PasswordEncoder passwordEncoder) {
        this.dbTravellerUserService = dbTravellerUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().and().csrf().disable().
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/", "/users/login", "/users/register").permitAll().
                antMatchers("/admin/**","/users/change-roles/**", "/users/all").hasRole("ADMIN").
                antMatchers(
                        "/hotels/create",
                        "/hotels/add-room/**",
                        "/hotels/edit/**",
                        "/hotels/owned",
                        "/hotels/reservations/**",
                        "/hotels/api/owned",
                        "/picture/delete").hasRole("HOTEL_OWNER").
                antMatchers("/**").authenticated().
                and().
                formLogin().
                loginPage("/users/login").
                usernameParameter("email").
                passwordParameter("password").
                defaultSuccessUrl("/").
                failureForwardUrl("/users/login-error").
                and().
                logout().
                logoutUrl("/users/logout").
                logoutSuccessUrl("/").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(dbTravellerUserService)
                .passwordEncoder(passwordEncoder);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
