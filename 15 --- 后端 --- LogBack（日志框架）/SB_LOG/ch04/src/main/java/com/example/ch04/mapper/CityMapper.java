package com.example.ch04.mapper;

import com.example.ch04.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    List<City> listCity(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
