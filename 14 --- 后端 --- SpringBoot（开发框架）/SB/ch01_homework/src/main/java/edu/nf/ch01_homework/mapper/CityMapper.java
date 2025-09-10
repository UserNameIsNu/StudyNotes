package edu.nf.ch01_homework.mapper;

import edu.nf.ch01_homework.entity.City;

import java.util.List;

public interface CityMapper {
    List<City> selectForId(String id);
}
