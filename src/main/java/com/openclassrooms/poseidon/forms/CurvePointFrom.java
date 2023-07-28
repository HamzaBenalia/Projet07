package com.openclassrooms.poseidon.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointFrom {

    @NotBlank(message = "curveId is required")
    @Pattern(message = "id must be a number", regexp = "^[0-9]*$")
    private String curveId;

    @NotBlank(message = "term is required")
    private String term;

    @Pattern(message = "value must be a number", regexp = "^[0-9]*$")
    @NotBlank(message = "value is required")
    private String value;
}
