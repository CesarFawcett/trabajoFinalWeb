package aeroline.nr.api.services;

import java.util.List;
import java.util.Optional;
import aeroline.nr.api.entities.Booking;
import aeroline.nr.api.entities.BookingStatus;

public interface BookingService {
    void delete(Integer id);
    Booking createBooking(Booking newBooking);
    List<Booking> findAll();
    Optional<Booking> findById(Integer id);
    List<Booking> findBookingsByStatusAndCustomerName(BookingStatus status, String customerName);
    List<Booking> findBookingsByStatus(BookingStatus status);
    List<Booking> findBookingsByCustomerName(String customerName);
    List<Booking> findBookingByOutboundFlight(Integer flight_Id);
}
