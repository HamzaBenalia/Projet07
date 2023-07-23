package com.openclassrooms.poseidon.forms;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidListForm {
    @NotBlank(message = "id is mandatory")
    private String bidListId;
    @NotBlank(message = "account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @NotBlank(message = "bidQuantity is mandatory")
    private String bidQuantity;
}