package kz.auto_life.authservice.entities;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String email;
    private String password;
}
