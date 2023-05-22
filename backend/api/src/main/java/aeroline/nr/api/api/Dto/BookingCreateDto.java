package aeroline.nr.api.api.Dto;

import aeroline.nr.api.entities.BookingStatus;
import aeroline.nr.api.entities.Flight;
import aeroline.nr.api.entities.User;
import lombok.Data;

@Data
public class BookingCreateDto {
    private int id;
    private BookingStatus status;
    private Flight utboundFlight;
    private String paymentToken;
    private boolean checkedIn;
    private User customer;
    private String createdAt;
    private String bookingReference;
}
