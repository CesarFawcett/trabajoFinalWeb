package aeroline.nr.api.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @ManyToOne( optional = true)
    //@JoinColumn(name = "flightId")
    private Flight outboundFlight;
    private boolean checkedIn;
    @ManyToOne(optional = true)
    //@JoinColumn(name = "userId")
    private User customerName;
    private String createdAt;
    private String bookingReference;
}
