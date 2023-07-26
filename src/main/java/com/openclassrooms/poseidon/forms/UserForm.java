package com.openclassrooms.poseidon.forms;

import com.openclassrooms.poseidon.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "fullName is required")
    private String fullName;

//    @NotBlank(message = "password is required")
    @ValidPassword
    private String password;

    @NotBlank(message = "role is required")
    private String role;
}
