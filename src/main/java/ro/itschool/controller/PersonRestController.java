package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Person;
import ro.itschool.repository.PersonRepository;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor

public class PersonRestController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Person>> getAllPersons() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable Integer id) {
        return personRepository.findById(id).get();
    }

    @GetMapping(value = "/all-by-name-and-address")
    public ResponseEntity getAllCarsByManufacturer(@RequestParam  String name, @RequestParam String address) {
        List<Person> persons = personRepository.findByNameAndAddress(name, address);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-age")
    public List<Person> getPersonsByAgeBetween(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return personRepository.findByAgeBetween(minAge, maxAge);
    }


    @PostMapping
    public void savePerson(@RequestBody Person person) {
        person.setAddDate(LocalDate.now());
        personRepository.save(person);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deletePerson(@PathVariable Integer id) {
        if (personRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(String.format("Person with id %d was not found", id), HttpStatus.BAD_REQUEST);
        }
        personRepository.findById(id).ifPresent(car -> personRepository.deleteById(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update")
    public void updatePerson(@RequestBody Person person) {
        personRepository.save(person);
    }
    
}
