package ro.itschool.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {


  List<Person> findByNameAndAddress(String name, String address);
}
