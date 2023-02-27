package kz.auto_life.authservice.services;

import kz.auto_life.authservice.entities.User;

public interface UserService {
    User register(String email, String password);
    User save(User user);
}
