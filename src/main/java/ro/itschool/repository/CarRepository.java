package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Car c where c.deleted = true and DATE(c.deletionDate) < '2015-12-31'")
    void deleteDeletedCarsOlderThan2016();


}
