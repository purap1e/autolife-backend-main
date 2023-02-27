package kz.auto_life.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.auto_life.authservice.entities.UserRegisterRequest;
import kz.auto_life.authservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Авторизация",
            description = "Апи для авторизации и получение токена")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/registration")
    public ResponseEntity<?> regsiterNewUser(@RequestBody UserRegisterRequest request) {
        userService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().build();
    }
//    public String test() {
//        return "Добро пожаловать!!!";
//    }
}
