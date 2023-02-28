package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/index", "/"})
@RequiredArgsConstructor
public class IndexController {

    private final CarRepository carRepository;

    //-----------------Home page--------------------------
    //-----------------Display all cars-------------------
    @RequestMapping
    public String getIndex(Model model) {
        List<Car> allCars = carRepository.findAll();
        model.addAttribute("cars", allCars);
        return "index";
    }
    //----------------------------------------------------

}
