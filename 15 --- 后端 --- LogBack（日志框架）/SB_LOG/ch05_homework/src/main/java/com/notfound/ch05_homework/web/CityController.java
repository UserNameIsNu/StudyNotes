package com.notfound.ch05_homework.web;

import com.notfound.ch05_homework.entity.City;
import com.notfound.ch05_homework.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController implements CityService {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list/city")
    public List<City> listCity(int pageNum, int pageSize) {
        return cityService.listCity(pageNum, pageSize);
    }
}
