package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.RegionElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.RegionRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.RegionResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.RegionSearch;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/region")
@RestController
public class RegionController {

    @Autowired
    private RegionService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseMessage<RegionResponse> findById(
            @PathVariable Integer id
    ) {
        Region entity = service.findById(id);
        if(entity != null) {
            RegionResponse data = modelMapper.map(entity, RegionResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<RegionResponse> add(
            @RequestBody @Valid RegionRequest model
    ) {
        Region entity = modelMapper.map(model, Region.class);
        entity = service.save(entity);

        RegionResponse data = modelMapper.map(entity, RegionResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("{id}")
    public ResponseMessage<RegionResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid RegionRequest model
    ) {
        Region entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        modelMapper.map(model, entity);
        entity = service.save(entity);

        RegionResponse data = modelMapper.map(entity, RegionResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<RegionResponse> delete(
            @PathVariable Integer id
    ) {
        Region entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        RegionResponse data = modelMapper.map(entity, RegionResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<RegionResponse>> findAll() {
        List<Region> entities = service.findAll();
        List<RegionResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, RegionResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<RegionElement>> findAll(
            @Valid RegionSearch model
    ) {
        Region search = modelMapper.map(model, Region.class);

        Page<Region> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Region> entities = entityPage.toList();

        List<RegionElement> models = entities.stream()
                .map(e -> modelMapper.map(e, RegionElement.class))
                .collect(Collectors.toList());

        PagedList<RegionElement> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );
        return ResponseMessage.success(data);
    }
}