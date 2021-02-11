package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionImpl extends CommonServiceImpl<Region, Integer> implements RegionService {

    @Autowired
    protected RegionImpl(JpaRepository<Region, Integer> repository) {
        super(repository);
    }
}
