package com.openclassrooms.poseidon.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointFrom {

    @NotBlank(message = "curveId is required")
    private String curveId;

    @NotBlank(message = "term is required")
    private String term;

    @NotBlank(message = "value is required")
    private String value;
}
