package ru.rtu_mirea.musspringbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/artist/**", "/album/**", "/song/**", "/registration").permitAll()
                .antMatchers("account/**").authenticated()
                .antMatchers("/").hasAuthority(Role.SUPER_ADMIN.getAuthority())
                .antMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.getAuthority(), Role.SUPER_ADMIN.getAuthority())
                .antMatchers("/user/**").hasAuthority(Role.USER.getAuthority())
                .anyRequest().authenticated().and().httpBasic()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

    /**
     * Authentication provider.
     * @param userRepo user repository
     * @return authentication provider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserRepo userRepo) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Password encoder.
     * @return password encoder
     */
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
