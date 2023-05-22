package aeroline.nr.api.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import aeroline.nr.api.api.Dto.FlightCreateDto;
import aeroline.nr.api.api.Dto.FlightDto;
import aeroline.nr.api.api.Dto.FlightMapper;
import aeroline.nr.api.entities.Flight;
import aeroline.nr.api.exceptions.DuplicateCodigoException;
import aeroline.nr.api.exceptions.FlightNotFoundException;
import aeroline.nr.api.services.FlightService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Reemplaza con la URL de tu aplicación de React
@RequestMapping("/catalog")
public class FlightController {
    @Autowired
    private  FlightService flightService;
    @Autowired
    private  FlightMapper flightMapper;
    
    public FlightController(FlightService flightService, FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }
    @PostConstruct
    public void init1() {
        Flight newFlight = new Flight();
        newFlight.setDepartureDate("2022-04-29");
        newFlight.setDepartureAirportCode("LON");
        newFlight.setDepartureAirportName("Airport 1");
        newFlight.setDepartureCity("City 1");
        newFlight.setDepartureLocale("Locale 1");
        newFlight.setArrivalDate("2023-05-21");
        newFlight.setArrivalAirportCode("ARG");
        newFlight.setArrivalAirportName("Airport 2");
        newFlight.setArrivalCity("City 2");
        newFlight.setArrivalLocale("Locale 2");
        newFlight.setTicketPrice(100);
        newFlight.setTicketCurrency("USD");
        newFlight.setFlightNumber(123);
        newFlight.setSeatCapacity(200);

        flightService.create(newFlight);
    }
    @PostConstruct
    public void init2() {
        Flight newFlight = new Flight();
        newFlight.setDepartureDate("2022-04-20");
        newFlight.setDepartureAirportCode("MAD");
        newFlight.setDepartureAirportName("Airport 3");
        newFlight.setDepartureCity("City 2");
        newFlight.setDepartureLocale("Locale 1");
        newFlight.setArrivalDate("2023-04-25");
        newFlight.setArrivalAirportCode("SIR");
        newFlight.setArrivalAirportName("Airport 4");
        newFlight.setArrivalCity("City 2");
        newFlight.setArrivalLocale("Locale 2");
        newFlight.setTicketPrice(150);
        newFlight.setTicketCurrency("USD");
        newFlight.setFlightNumber(123);
        newFlight.setSeatCapacity(200);

        flightService.create(newFlight);
    }
    @GetMapping("/")
    public ResponseEntity<List<FlightDto>> findAll(){
        List<Flight> flights =flightService.findAll();
        List<FlightDto> flightDtos = flights.stream()
                                             .map(t -> flightMapper.toFlightDto(t))
                                             .collect(Collectors.toList());
            return ResponseEntity.ok().body(flightDtos);
    }
     //1 Obtiene la lista de todos los vuelos que cumplan con los criterios de búsqueda establecidos en el query parameter
    //se buscan enviando los 3 parametros o si se envian de 2
    @GetMapping("")
    ResponseEntity<List<FlightDto>> findFlightsByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode(
                                @RequestParam (required = false)String departureDate,
                                @RequestParam (required = false)String departureAirportCode,
                                @RequestParam (required = false)String arrivalAirportCode) {
    List<Flight> flights;
    if(departureAirportCode == null){
        flights = flightService.findFlightsByDepartureDateAndArrivalAirPortCode(departureDate, arrivalAirportCode);
    } else if(arrivalAirportCode == null){
        flights = flightService.findFlightsByDepartureDateAndDepartureAirportCode(departureDate, departureAirportCode); 
    }else if(departureDate == null){
        flights = flightService.findFlightsByArrivalAirportCodeAndDepartureAirporCode(arrivalAirportCode, departureAirportCode);
    }else{
        flights = flightService.findFlightsByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode(departureDate, departureAirportCode, arrivalAirportCode);
    }
    List<FlightDto> flightDtos = flights.stream()
            .map(FlightMapper::fromFlight)
            .collect(Collectors.toList());
        return new ResponseEntity<>(flightDtos, HttpStatus.OK);
    }
     //2 Permite crear un nuevo vuelo
    @PostMapping("/")  
    public ResponseEntity<FlightCreateDto> createFlight(@RequestBody FlightCreateDto flight){ 
        Flight newFlight = flightMapper.toEntity(flight);
        Flight flightCreated = null;
         try {
            flightCreated = flightService.createFlight(newFlight);
         } catch (Exception e) {
             throw new DuplicateCodigoException();
         }
         FlightCreateDto flightCreateDto = flightMapper.toFlightCreateDto(flightCreated);
         URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                         .path("/{id}")
                         .buildAndExpand(flightCreateDto.getId())
                         .toUri();
         return ResponseEntity.created(location).body(flightCreateDto);
    }
    //3 Actualiza un vuelo del catálogo por el id
    
    @PutMapping("/{id}")
    public ResponseEntity<FlightCreateDto> updateFlight(@PathVariable("id") Integer id, @RequestBody FlightCreateDto flight) {
    Optional<Flight> optionalFlight = flightService.fainId(id);
    if (optionalFlight.isEmpty()) {
        throw new ObjectNotFoundException("Flight not found with id: " + id, null);
    }
    Flight flightToUpdate = flightMapper.toEntity(flight);
    Optional<Flight> updatedFlight = flightService.updateFlight(id, flightToUpdate);
    if (updatedFlight.isPresent()) {
        FlightCreateDto updatedDto = flightMapper.toFlightCreateDto(updatedFlight.get());
        return ResponseEntity.ok().body(updatedDto);
    } else {
        FlightCreateDto createdDto = flightMapper.toFlightCreateDto(flightToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdDto);
    }
    }
    //4 /Elimina un vuelo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
    flightService.delete(id);
    return ResponseEntity.noContent().build();
    }
    
    //5 Obtiene un listado de los vuelos que salen de un aeropuerto en una fecha determinada
    ///MAD/?departureDate=2022-05-12
    @GetMapping("/{departureAirportCode}/")
    public ResponseEntity<List<FlightDto>> findFlightByDepartureAirportCodeAndDepartureDate(
        @PathVariable String departureAirportCode,
        @RequestParam String departureDate) {
    List<Flight> flight = flightService.findByDepartureAirportCodeAndDepartureDate(
            departureAirportCode, departureDate);
    if (flight.isEmpty()) {
        throw new FlightNotFoundException("No se encontraron vuelos para la fecha y aeropuerto especificados.");
    }
    List<FlightDto> flightDtos = flight.stream()
            .map(FlightMapper::fromFlight)
            .collect(Collectors.toList());
    return new ResponseEntity<>(flightDtos, HttpStatus.OK);
}
}   
