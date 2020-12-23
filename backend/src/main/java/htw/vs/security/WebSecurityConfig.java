package htw.vs.security;

import htw.vs.base.CONFIG;
import htw.vs.data.Role;
import htw.vs.data.User;
import htw.vs.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Web security config.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final String INITIAL_ADMIN_PASSWORD = CONFIG.DEFAULT_ADMIN_PASSWORD;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;


    public WebSecurityConfig(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        try {
            userDetailsService.loadUserByUsername("supervisor");
        } catch (UsernameNotFoundException e) {
            User supervisor = new User();
            supervisor.setUserName("supervisor");
            supervisor.setPassword(passwordEncoder().encode(INITIAL_ADMIN_PASSWORD));
            supervisor.setEnabled(true);
            Role adminRole = new Role();
            Role coordinatorRole = new Role();
            Role userRole = new Role();
            adminRole.getUsers().add(supervisor);
            coordinatorRole.getUsers().add(supervisor);
            userRole.getUsers().add(supervisor);
            adminRole.setName("SUPERVISOR");
            coordinatorRole.setName("COORDINATOR");
            userRole.setName("USER");
            supervisor.getRoles().add(adminRole);
            supervisor.getRoles().add(coordinatorRole);
            supervisor.getRoles().add(userRole);
            userRepository.save(supervisor);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.httpBasic()
                .and().authorizeRequests().antMatchers("/swagger**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }
}