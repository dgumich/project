package ru.innopolis.university.stc27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.university.stc27.domain.Role;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return null;
    }

    public boolean addUser(String username, String login, String email, String password) {
        User userFromDB =  userRepository.findUserBylogin(login);
        if (userFromDB != null) {
            return false;
        }

        User user = new User(username, login, password, email);

        user.setRoles(Collections.singleton(Role.USER));
        user.setCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getMail())) {
            String message = String.format("Hello, %s!\n" + "Welcome to Awesome application! " +
                    "Please, visit next link http://localhost:8080/activate/%s", username, user.getCode());
            mailSender.send(email, "Activation code", message);

        }

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findUserByCode(code);

        if (user == null) {
            return false;
        }

        user.setCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    public User getUser(String login) {
        return userRepository.findUserBylogin(login);
    }
}
