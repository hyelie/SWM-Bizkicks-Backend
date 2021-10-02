package com.bizkicks.backend.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class PasswordDto {
    private String old_password;
    private String new_password;
}
