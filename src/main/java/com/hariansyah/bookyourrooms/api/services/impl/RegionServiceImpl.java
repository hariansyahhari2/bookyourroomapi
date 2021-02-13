package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl extends CommonServiceImpl<Region, Integer> implements RegionService {

    @Autowired
    protected RegionServiceImpl(JpaRepository<Region, Integer> repository) {
        super(repository);
    }
}
