package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

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
        carRepository.save(car);
        return "redirect:/index";
    }
    //----------------------------------------------------------------

    //--------------------Delete car by id---------------------------- /car/delete/' + ${car.carId}

    @RequestMapping(value = "/delete/{id}")
    public String deleteCar(@PathVariable Integer id) {
        carRepository.deleteById(id);
        return "redirect:/index";
    }

    //----------------------------------------------------------------
}
