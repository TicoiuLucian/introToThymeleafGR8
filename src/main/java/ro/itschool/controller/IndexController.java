package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.repository.CarRepository;

import java.util.List;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {

    private final CarRepository carRepository;

    //-----------------Home page--------------------------
    //-----------------Display all cars-------------------
    @RequestMapping
    public String getIndex(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "index";
    }
    //----------------------------------------------------

}
