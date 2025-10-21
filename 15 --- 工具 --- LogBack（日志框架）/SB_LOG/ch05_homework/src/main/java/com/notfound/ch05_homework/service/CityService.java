package com.notfound.ch05_homework.service;

import com.notfound.ch05_homework.entity.City;

import java.util.List;

public interface CityService {
    List<City> listCity(int pageNum, int pageSize);
}
