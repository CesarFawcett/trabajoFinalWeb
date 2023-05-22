package aeroline.nr.api.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(){
        super("Booking not Found");
     }
     public BookingNotFoundException(String messaje){
        super(messaje);
     }
}
