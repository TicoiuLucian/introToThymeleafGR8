package ro.itschool.scheduler;

import com.github.javafaker.Faker;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

import java.time.LocalDate;


@Component
@Getter
@Setter
@NoArgsConstructor
@Log
public class InsertCarScheduler {

    @Autowired
    private CarRepository carRepository;


    //<second> <minute> <hour> <day-of-month> <month> <day-of-week>
    //0/x = every x seconds
    // – (range) => 0-5 seconds 0 to 5
    // https://www.baeldung.com/cron-expressions

    //    UNCOMMENT BELOW FOR SCHEDULING
    @Scheduled(cron = "0/10 * * * * *")
    public void insertCarIntoDb() {
        Faker faker = new Faker();
        Car car = new Car();
        car.setModel(faker.animal().name());
        car.setManufacturer(faker.company().name());
        car.setPrice((float) faker.number().numberBetween(1000, 10000));
        car.setDeleted(faker.bool().bool());
        if (car.getDeleted()) {
            car.setDeletionDate(LocalDate.of(
                    faker.number().numberBetween(2000, 2023),
                    faker.number().numberBetween(1, 12),
                    faker.number().numberBetween(1, 31)));
        }
        log.info("Insert car: " + car);
        carRepository.save(car);
    }


    @Scheduled(cron = "0/30 * * * * *")
    public void deleteDeletedCarsOlderThan2015() {
        carRepository.deleteDeletedCarsOlderThan2016();
        log.info("Deleting cars older than 2015...");
    }
}
