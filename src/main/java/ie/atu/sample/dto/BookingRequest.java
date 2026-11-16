package ie.atu.sample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookingRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotBlank(message = "Organiser name must not be blank")
    @Size(min = 2, max = 60, message = "Organiser name must be 2 to 60 characters")
    private String organiserName;

    @Positive(message = "Attendee count must be a positive whole number")
    private int attendeeCount;
}
