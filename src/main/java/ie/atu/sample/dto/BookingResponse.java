package ie.atu.sample.dto;

import ie.atu.sample.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long roomId;
    private String roomName;
    private String organiserName;
    private int attendeeCount;
    private BookingStatus status;
}
