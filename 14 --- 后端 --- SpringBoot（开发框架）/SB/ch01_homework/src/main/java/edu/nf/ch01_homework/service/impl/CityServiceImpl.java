package edu.nf.ch01_homework.service.impl;

import edu.nf.ch01_homework.entity.City;
import edu.nf.ch01_homework.mapper.CityMapper;
import edu.nf.ch01_homework.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private CityMapper cityMapper;

    public CityServiceImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public List<City> selectForId(String id) {
        return cityMapper.selectForId(id);
    }
}
