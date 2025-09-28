package com.example.ch11_homework.service;

import com.example.ch11_homework.entity.City;

import java.util.List;

public interface CityService {
    List<City> listCity(int pageNum, int pageSize);
    List<City> listCityPro(int pageNum, int pageSize);
    List<City> listCityProMax(int pageNum, int pageSize);
    List<City> listCityProMaxUltra(int pageNum, int pageSize);
}
