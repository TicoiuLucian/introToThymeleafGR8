package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Car;
import ro.itschool.repository.CarRepository;

@Controller
@RequestMapping({"/index", "/"})
@RequiredArgsConstructor
public class IndexController {

    private final CarRepository carRepository;

    //-----------------Home page--------------------------
    //-----------------Display all cars-------------------
    @RequestMapping
    public String getIndex(Model model, @RequestParam(required = false) String keyword,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(defaultValue = "carId,asc") String[] sort,
                           @RequestParam(required = false) Integer minPrice,
                           @RequestParam(required = false) Integer maxPrice) {
        String sortField = sort[0];
        String sortDirection = sort[1];

        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Car> pageCars;
        if (keyword == null) {
            pageCars = carRepository.findAll(pageable);
            if (maxPrice != null && minPrice != null)
                pageCars = carRepository.findCarsWithPriceBetween(minPrice, maxPrice, pageable);
        } else {
            pageCars = carRepository.findCarByKeyword(keyword, pageable);
        }


        model.addAttribute("cars", pageCars.getContent());
        model.addAttribute("currentPage", pageCars.getNumber() + 1);
        model.addAttribute("totalItems", pageCars.getTotalElements());
        model.addAttribute("totalPages", pageCars.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");


        return "index";
    }
    //----------------------------------------------------

}
