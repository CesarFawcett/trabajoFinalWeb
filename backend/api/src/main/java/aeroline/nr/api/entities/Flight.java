package aeroline.nr.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="flights")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String departureDate;
    @Column(nullable = false)
    @JoinColumn(name = "departureAirportCode")
    private String departureAirportCode;
    private String departureAirportName;
    private String departureCity;
    private String departureLocale;
    private String arrivalDate;
    @Column(nullable = false)
    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalCity;
    private String arrivalLocale;
    private int ticketPrice;
    private String ticketCurrency;
    private int flightNumber;
    private int seatCapacity; 
   // @OneToMany(mappedBy = "outboundFlight")
    //private List<Booking> bookings = new ArrayList<>();
}
