package com.book.store.dto;

import com.book.store.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @NotBlank
    @Size(min = 2, max = 36)
    private String name;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String phone;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(min = 2, max = 48)
    private String nationality;

    public static User toEntity(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setPhone(registerRequest.getPhone());
        user.setEmail(registerRequest.getEmail());
        user.setNationality(registerRequest.getNationality());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

}
