package com.example.payment.dao;

import com.example.payment.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDao {
    User create(User user);
    User findById(String id);
    User update(String id, User user);
    User delete(String id);
}
