package com.example.payment.dao;

import com.example.payment.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBuilder implements UserDao {
    private final UserRepository repository;

    public UserBuilder(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User update(String id, User user) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            return repository.save(optionalUser.get());
        }
        return null;
    }


    @Override
    public User delete(String id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            repository.deleteById(id);
            return optionalUser.get();
        }
        return null;
    }
}
