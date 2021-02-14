package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.repositories.CompanyRepository;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public Company findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(Company entity) {
        return repository.save(entity);
    }

    @Override
    public Boolean edit(Company entity) {
        return repository.edit(entity);
    }

    @Override
    public Boolean removeById(Integer id) {
        return repository.remove(id);
    }
}
