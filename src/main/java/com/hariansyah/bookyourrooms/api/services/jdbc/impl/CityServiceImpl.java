package com.hariansyah.bookyourrooms.api.services.jdbc.impl;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.repositories.jdbc.CityRepository;
import com.hariansyah.bookyourrooms.api.services.jdbc.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository repository;

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public City findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(City entity) {
        return repository.save(entity);
    }

    @Override
    public Boolean edit(City entity) {
        return repository.edit(entity);
    }

    @Override
    public Boolean removeById(Integer id) {
        return repository.remove(id);
    }
}
