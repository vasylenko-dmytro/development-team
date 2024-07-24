package com.vasylenko.application.service;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.user.CreateUserParameters;
import com.vasylenko.application.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(CreateUserParameters parameters);
    Page<User> getUsers(Pageable pageable); //<.>
    boolean userWithEmailExists(Email email);
}
