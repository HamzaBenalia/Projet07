package com.openclassrooms.poseidon.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidListForm {
    @NotBlank(message = "id is mandatory")
    @Pattern(message = "id must be a number", regexp = "^[0-9]*$")
    private String bidListId;
    @NotBlank(message = "account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @NotBlank(message = "bidQuantity is mandatory")
    @Pattern(message = "bidQuantity must be a number", regexp = "^[1-9]*$")
    private String bidQuantity;
}
