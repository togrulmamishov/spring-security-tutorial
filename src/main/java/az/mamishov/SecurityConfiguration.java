package az.mamishov;

import az.mamishov.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

//        auth.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=springframework,dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword");

//        String usersQuery = "select username, password, enabled from users where username = ?";
//        String authQuery = "select username, authority from authorities where username = ?";
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema();
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(authQuery);

//        auth.inMemoryAuthentication()
//                .withUser("blah")
//                .password("blah")
//                .roles("USER")
//                .and()
//                .withUser("foo")
//                .password("foo")
//                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/**/user").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
