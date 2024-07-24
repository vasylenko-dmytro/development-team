package com.vasylenko.application.service;

import com.vasylenko.application.exception.UserNotFoundException;
import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.user.CreateUserParameters;
import com.vasylenko.application.model.user.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(CreateUserParameters parameters) {
        UserId userId = repository.nextId();
        User user = new User(userId,
                parameters.getUserName(),
                parameters.getGender(),
                parameters.getBirthday(),
                parameters.getEmail(),
                parameters.getPhoneNumber());
        return repository.save(user);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public boolean userWithEmailExists(Email email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User editUser(UserId userId, EditUserParameters parameters) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (parameters.getVersion() != user.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(User.class, user.getId().asString());
        }
        parameters.update(user);
        return user;
    }

    @Override
    public Optional<User> getUser(UserId userId) {
        return repository.findById(userId);
    }
}
