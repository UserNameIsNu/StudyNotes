package com.notfound.ch05_homework.service.impl;

import com.notfound.ch05_homework.entity.City;
import com.notfound.ch05_homework.mapper.CityMapper;
import com.notfound.ch05_homework.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityMapper cityMapper;

    public CityServiceImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public List<City> listCity(int pageNum, int pageSize) {
        return cityMapper.listCity(pageNum, pageSize);
    }
}
