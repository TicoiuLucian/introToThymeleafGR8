package ro.itschool.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.entity.Person;
import ro.itschool.repository.PersonRepository;

@RestController
@RequestMapping(value = "/rest/person")
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
  public ResponseEntity getAllPersonsByNameAndAddress(@RequestParam String name, @RequestParam String address) {
    List<Person> persons = personRepository.findByNameAndAddress(name, address);
    return new ResponseEntity<>(persons, HttpStatus.OK);
  }


  @PostMapping
  public void savePerson(@RequestBody Person person) {
    personRepository.save(person);
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity deletePerson(@PathVariable Integer id) {
    if (personRepository.findById(id).isEmpty()) {
      return new ResponseEntity<>(String.format("Person with id %d was not found", id), HttpStatus.BAD_REQUEST);
    }
    personRepository.findById(id).ifPresent(p ->personRepository.deleteById(id));
    return ResponseEntity.ok().build();
  }

  @PutMapping(value = "/update")
  public void updatePerson(@RequestBody Person person) {
    personRepository.save(person);
  }
}
