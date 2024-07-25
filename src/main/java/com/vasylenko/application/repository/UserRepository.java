package com.vasylenko.application.repository;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, UserId>, PagingAndSortingRepository<User, UserId> {
    UserId nextId();
    boolean existsByEmail(Email email);
    Optional<User> findByEmail(Email email);
}
