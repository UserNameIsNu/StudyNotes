package edu.nf.ch01_homework.service;

import edu.nf.ch01_homework.entity.City;

import java.util.List;

public interface CityService {
    List<City> selectForId(String id);
}
