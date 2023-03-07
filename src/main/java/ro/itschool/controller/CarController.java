package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    //------------Add new car to database--------------------------------
    @GetMapping(value = "/add")
    public String addCar(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "add-car.html";
    }

    @PostMapping(value = "/add")
    public String processForm(@ModelAttribute(value = "car") Car car) {
        car.setAddDate(LocalDate.now());
        carRepository.save(car);
        return "redirect:/index";
    }
    //----------------------------------------------------------------

    //--------------------Sell car by id----------------------------
    @RequestMapping(value = "/sell/{id}")
    public String deleteCar(@PathVariable Integer id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        optionalCar.ifPresent(car -> {
            car.setSold(true);
            car.setSoldDate(LocalDate.now());
            carRepository.save(car);
        });
        return "redirect:/index";
    }
    //----------------------------------------------------------------

    //--------------------Edit car by id---------------------------- /car/delete/' + ${car.carId}
    @RequestMapping(value = "/edit/{id}")
    public String editCar(@PathVariable Integer id, Model model) {
        model.addAttribute("carToBeEdited", carRepository.findById(id));
        return "edit-car";
    }

    @RequestMapping(value = "/edit")
    public String editAndSaveEditedCar(@ModelAttribute(value = "car") Car car) {
        carRepository.save(car);
        return "redirect:/index";
    }
    //----------------------------------------------------------------


    //--------------View sold cars--------------------------------
    @RequestMapping(value = "/sold")
    public String viewSoldCars(Model model) {
        List<Car> soldCars = carRepository.findSoldCars();
        model.addAttribute("soldCars", Objects.requireNonNullElseGet(soldCars, ArrayList::new));
        return "sold-cars";
    }
    //------------------------------------------------------------

}
