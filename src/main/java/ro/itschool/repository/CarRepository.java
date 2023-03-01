package ro.itschool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM Car c where c.deleted = true and DATE(c.deletionDate) < curdate() - INTERVAL 30 DAY")
//    void deleteSoldCarsOlderThan30Days();


    @Query(value = "SELECT * FROM Car c where c.sold = 1", nativeQuery = true)
    List<Car> findSoldCars();

    @Query(value = """
            SELECT * FROM Car c WHERE 
             c.sold = 0 AND 
             (c.manufacturer LIKE %:keyword% OR
             c.model LIKE %:keyword% OR
             c.price LIKE %:keyword%) 
             """,
            nativeQuery = true)
    Page<Car> findCarByKeyword(String keyword, Pageable pageable);
}
