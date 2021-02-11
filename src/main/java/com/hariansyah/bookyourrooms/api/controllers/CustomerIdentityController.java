package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.CustomerIdentityModel;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.RegionResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.CustomerIdentitySearch;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customer-identity")
@RestController
public class CustomerIdentityController {

    @Autowired
    private CustomerIdentityService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseMessage<CustomerIdentityModel> findById(
            @PathVariable Integer id
    ) {
        CustomerIdentity entity = service.findById(id);
        if(entity != null) {
            CustomerIdentityModel data = modelMapper.map(entity, CustomerIdentityModel.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<CustomerIdentityModel> add(
            @RequestBody @Valid CustomerIdentityModel model
    ) {
        CustomerIdentity entity = modelMapper.map(model, CustomerIdentity.class);
        entity = service.save(entity);

        CustomerIdentityModel data = modelMapper.map(entity, CustomerIdentityModel.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("{id}")
    public ResponseMessage<CustomerIdentityModel> edit(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerIdentityModel model
    ) {
        CustomerIdentity entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        modelMapper.map(model, entity);
        entity = service.save(entity);

        CustomerIdentityModel data = modelMapper.map(entity, CustomerIdentityModel.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<CustomerIdentityModel> delete(
            @PathVariable Integer id
    ) {
        CustomerIdentity entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        CustomerIdentityModel data = modelMapper.map(entity, CustomerIdentityModel.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<CustomerIdentityModel>> findAll() {
        List<CustomerIdentity> entities = service.findAll();
        List<CustomerIdentityModel> data = entities.stream()
                .map(e -> modelMapper.map(e, CustomerIdentityModel.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<CustomerIdentityModel>> findAll(
            @Valid CustomerIdentitySearch model
    ) {
        CustomerIdentity search = modelMapper.map(model, CustomerIdentity.class);

        Page<CustomerIdentity> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<CustomerIdentity> entities = entityPage.toList();

        List<CustomerIdentityModel> models = entities.stream()
                .map(e -> modelMapper.map(e, CustomerIdentityModel.class))
                .collect(Collectors.toList());

        PagedList<CustomerIdentityModel> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );
        return ResponseMessage.success(data);
    }
}