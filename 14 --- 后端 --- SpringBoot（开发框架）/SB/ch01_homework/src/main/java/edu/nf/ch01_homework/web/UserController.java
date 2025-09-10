package edu.nf.ch01_homework.web;

import edu.nf.ch01_homework.entity.City;
import edu.nf.ch01_homework.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private CityService cityService;

    public UserController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/user/test")
    public List<City> test(@RequestParam String id) {
        System.out.println("come in");
        return cityService.selectForId(id);
    }
}
