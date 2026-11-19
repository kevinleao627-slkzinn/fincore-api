package com.kevin.fincore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest (
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    String name,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail não é válido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
    String password
){
}