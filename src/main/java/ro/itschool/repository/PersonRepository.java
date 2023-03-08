package ro.itschool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.itschool.entity.Car;
import ro.itschool.entity.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "SELECT * FROM PERSON p where p.id = 1", nativeQuery = true)
    List<Person> findSoldPerson();

    @Query(value = """
            SELECT * FROM Person p WHERE 
             p.id = 0 AND 
             (p.name LIKE %:keyword% OR
             c.address LIKE %:keyword% OR
             c.age LIKE %:keyword%) 
             """,
            nativeQuery = true)
    Page<Person> findPersonByKeyword(String keyword, Pageable pageable);

    @Query(value = """
            SELECT * FROM PERSON p WHERE p.age BETWEEN :minAge AND :maxAge
            """, nativeQuery = true)
    Page<Car> findPersonsWithAgeBetween(Integer minAge, Integer maxAge, Pageable pageable);

    List<Car> findByName(String Name);

    List<Person> findByNameAndAddress(String Name, String Address);

    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);
}


