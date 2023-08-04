package com.openclassrooms.poseidon.forms;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingForm {

    @Pattern(message = "id must be a number", regexp = "^\\d+$")
    private String id;

    @Pattern(message = " moodysRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "moodysRating is required")
    private String moodysRating;

    @Pattern(message = "fitchRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "fitchRating is required")
    private String fitchRating;

    @Pattern(message = "orderNumber must be a number", regexp = "^\\d+$")
    @NotBlank(message = "orderNumber is required")
    private String orderNumber;

    @Pattern(message = " sandPRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "sandPRating is required")
    private String sandPRating;
}
