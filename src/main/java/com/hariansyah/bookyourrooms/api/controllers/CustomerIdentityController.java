package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CustomerIdentityRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CustomerIdentityResponse;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/customer-identity")
@RestController
public class CustomerIdentityController {

    @Autowired
    private CustomerIdentityService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseMessage<CustomerIdentityResponse> findById(
            @PathVariable Integer id
    ) {
        CustomerIdentity entity = service.findById(id);
        if(entity != null) {
            CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<CustomerIdentityResponse> add(
            @RequestBody @Valid CustomerIdentityRequest model
    ) {
        CustomerIdentity entity = modelMapper.map(model, CustomerIdentity.class);
        entity = service.save(entity);

        CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("{id}")
    public ResponseMessage<CustomerIdentityResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerIdentityRequest model
    ) {
        CustomerIdentity entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        modelMapper.map(model, entity);
        entity = service.save(entity);

        CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
        return ResponseMessage.success(data);
    }
}