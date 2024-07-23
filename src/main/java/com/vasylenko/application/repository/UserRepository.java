package com.vasylenko.application.repository;

import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, UserId> {
    UserId nextId();
}
