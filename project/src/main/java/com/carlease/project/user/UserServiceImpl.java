package com.carlease.project.user;

import com.carlease.project.enums.UserRole;
import com.carlease.project.exceptions.IncorrectPasswordException;
import com.carlease.project.exceptions.UserException;
import com.carlease.project.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserSession login(String username, String password) throws IncorrectPasswordException {
        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect username or password");
        }

        UserSession userSession = new UserSession();
        userSession.setUserId(user.getUserId());
        userSession.setRole(user.getRole());
        return userSession;
    }

    @Override
    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public static void validateUserRole(UserRepository userRepository, long id, UserRole role) throws UserException, UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getRole().equals(role)) {
            throw new UserException("User role does not match the provided role");
        }
    }
}