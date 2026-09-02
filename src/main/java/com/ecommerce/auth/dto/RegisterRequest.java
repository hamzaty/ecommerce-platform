package com.ecommerce.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Mot de passe obligatoire")
    private String password;

    @NotBlank(message = "Prénom obligatoire")
    private String firstName;

    @NotBlank(message = "Nom obligatoire")
    private String lastName;

    @NotBlank(message = "Numéro de téléphone obligatoire")
    private String phoneNumber;

    @NotBlank(message = "Adresse obligatoire")
    private String address;
}