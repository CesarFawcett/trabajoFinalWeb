package aeroline.nr.api.api.Dto;

import lombok.Data;

@Data
public class FlightDto {
    private int id;
    private String departureDate;
    private String departureAirportCode;
    private String departureCity;
    private String arrivalDate;
    private String arrivalAirportCode;
    private String arrivalCity;
    private int ticketPrice;
    private String ticketCurrency;
    private int flightNumber;
    private int seatCapacity;
}
