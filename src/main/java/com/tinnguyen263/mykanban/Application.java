package com.tinnguyen263.mykanban;

import com.tinnguyen263.mykanban.config.CustomUserDetails;
import com.tinnguyen263.mykanban.repository.UserRepository;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
        //Setup a default user if db is empty
//        if (repository.count()==0)
//            service.save(new User("user", "user", Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder());
    }

    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByUsername(username));
    }
}