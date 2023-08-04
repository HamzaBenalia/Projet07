package com.openclassrooms.poseidon.forms;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleNameForm {

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "json is required")
    private String json;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "sqlPart is required")
    private String sqlPart;

    @NotBlank(message = "sqlStr is required")
    private String sqlStr;

    @NotBlank(message = "template is required")
    private String template;
}
