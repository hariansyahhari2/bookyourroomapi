package com.hariansyah.bookyourrooms.api.services.jdbc.impl;

import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.repositories.jdbc.RegionRepository;
import com.hariansyah.bookyourrooms.api.services.jdbc.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository repository;

    @Override
    public List<Region> findAll() {
        return repository.findAll();
    }

    @Override
    public Region findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(Region entity) {
        return repository.save(entity);
    }

    @Override
    public Boolean edit(Region entity) {
        return repository.edit(entity);
    }

    @Override
    public Boolean removeById(Integer id) {
        return repository.remove(id);
    }
}
