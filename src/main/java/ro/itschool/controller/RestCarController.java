package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/car")
public class RestCarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public Car getCarById(@PathVariable Integer id) {
        return carRepository.findById(id).get();
    }

    @GetMapping(value = "/all-by-manufacturer-and-model")
    public ResponseEntity getAllCarsByManufacturer(@RequestParam String manufacturer, @RequestParam String model) {
        List<Car> cars = carRepository.findByManufacturerAndModel(manufacturer, model);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-price")
    public List<Car> getCarByPriceBetween(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return carRepository.findByPriceBetween(minPrice, maxPrice);
    }


    @PostMapping
    public void saveCar(@RequestBody Car car) {
        car.setAddDate(LocalDate.now());
        carRepository.save(car);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteCar(@PathVariable Integer id) {
        if (carRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(String.format("Car with id %d was not found", id), HttpStatus.BAD_REQUEST);
        }
        carRepository.findById(id).ifPresent(car -> carRepository.deleteById(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update")
    public void updateCar(@RequestBody Car car) {
        carRepository.save(car);
    }
}
