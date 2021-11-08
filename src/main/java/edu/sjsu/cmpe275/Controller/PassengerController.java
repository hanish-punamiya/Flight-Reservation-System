package edu.sjsu.cmpe275.Controller;

import edu.sjsu.cmpe275.Model.Passenger;
import edu.sjsu.cmpe275.Repository.PassengerRepository;
import edu.sjsu.cmpe275.Service.PassengerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    PassengerServiceImpl passengerService;

    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        try {
            List<Passenger> passengers = new ArrayList<Passenger>();

            passengerRepository.findAll().forEach(passengers::add);

            if (passengers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(passengers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") long id, @RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("phone") String phone) {
        Optional<Passenger> PassengerData = passengerRepository.findById(id);

        if (PassengerData.isPresent()) {
            Passenger _passenger = PassengerData.get();
            _passenger.setFirstName(firstName);
            _passenger.setLastName(lastName);
            _passenger.setAge(age);
            _passenger.setGender(gender);
            _passenger.setPhone(phone);
            return new ResponseEntity<>(passengerRepository.save(_passenger), HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            Map<String, Map> error = new HashMap<>();
            errorResponse.put("code", "404");
            errorResponse.put("msg", "User not found");
            error.put("BadRequest",errorResponse);
//            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable("id") long id) {
        Optional<Passenger> passenger = passengerService.getPassengerService(id);
        if (passenger == null) {
            Map<String, String> errorResponse = new HashMap<>();
            Map<String, Map> error = new HashMap<>();
            errorResponse.put("code", "404");
            errorResponse.put("msg", "Sorry, the requested passenger with ID " + id + " does not exist");
            error.put("BadRequest", errorResponse);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Passenger> createPassenger(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("phone") String phone) {
        boolean passengerExists = passengerService.createPassengerService(firstName, lastName, age, gender, phone);
        if (!passengerExists) {
            Map<String, String> errorResponse = new HashMap<>();
            Map<String, Map> error = new HashMap<>();
            errorResponse.put("code", "400");
            errorResponse.put("msg", "Another passenger with the same number already exists");
            error.put("BadRequest", errorResponse);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Passenger> deletePassenger(@PathVariable("id") long id){
        Optional<Passenger> passenger = passengerService.getPassengerService(id);
        if (passenger == null) {
            Map<String, String> errorResponse = new HashMap<>();
            Map<String, Map> error = new HashMap<>();
            errorResponse.put("code", "404");
            errorResponse.put("msg", "Passenger with ID " + id + " does not exist");
            error.put("BadRequest", errorResponse);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        passengerService.deletePassengerService(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
