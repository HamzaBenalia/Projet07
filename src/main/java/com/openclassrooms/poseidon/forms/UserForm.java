package com.openclassrooms.poseidon.forms;
import com.openclassrooms.poseidon.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    private String id;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @ValidPassword
    private String password;

    @NotBlank(message = "role is required")
    private String role;
}
