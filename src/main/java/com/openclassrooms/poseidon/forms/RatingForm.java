package com.openclassrooms.poseidon.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingForm {


    private String id;

    @NotBlank(message = "moodysRating is required")
    private String moodysRating;

    @NotBlank(message = "fitchRating is required")
    private String fitchRating;

    @NotBlank(message = "orderNumber is required")
    private String orderNumber;

    @NotBlank(message = "sandPRating is required")
    private String sandPRating;
}
