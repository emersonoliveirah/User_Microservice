package com.ms.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserRecordDTO(@NotBlank String name, @NotBlank @Email String email) {
    public UUID getId() {
        return UUID.randomUUID();
    }
}
