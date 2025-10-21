package com.notfound.ch05_homework.mapper;

import com.notfound.ch05_homework.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    List<City> listCity(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
