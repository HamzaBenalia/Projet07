package com.openclassrooms.poseidon.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeForm {

    @NotBlank(message = "account is required")
    private String account;

    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "buyQuantity is required")
    private String buyQuantity;
}
