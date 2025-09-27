package com.example.ch11_homework.web;

import com.example.ch11_homework.entity.City;
import com.example.ch11_homework.service.CityService;
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

    @GetMapping("/list/city/pro")
    public List<City> listCityPro(int pageNum, int pageSize) {
        return cityService.listCityPro(pageNum, pageSize);
    }

    @GetMapping("/list/city/promax")
    public List<City> listCityProMax(int pageNum, int pageSize) {
        return cityService.listCityProMax(pageNum, pageSize);
    }
}
