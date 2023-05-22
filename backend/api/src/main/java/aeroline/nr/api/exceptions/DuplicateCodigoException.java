package aeroline.nr.api.exceptions;

public class DuplicateCodigoException extends RuntimeException{
    public DuplicateCodigoException() {
        this("Codigo de la entidad duplicado");
    }
    public DuplicateCodigoException(String message) {
        super(message);
    } 
}
