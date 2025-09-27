package com.example.ch11_homework.mapper;

import com.example.ch11_homework.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    List<City> listCity(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
