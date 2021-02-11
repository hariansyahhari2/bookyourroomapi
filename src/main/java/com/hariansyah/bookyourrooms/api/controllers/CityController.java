package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.CityElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.RegionElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CityRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.CitySearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.CityService;
import com.hariansyah.bookyourrooms.api.services.RegionService;
import com.hariansyah.bookyourrooms.api.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/city")
@RestController
public class CityController {

    @Autowired
    private CityService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegionService customerIdentityService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<CityResponse> findById(
            @PathVariable Integer id
    ) {
        City entity = service.findById(id);
        if(entity != null) {
            CityResponse data = modelMapper.map(entity, CityResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<CityResponse> add(
            @RequestBody @Valid CityRequest model
    ) {
        City entity = modelMapper.map(model, City.class);

        Region customerIdentity = customerIdentityService.findById(model.getRegionId());

        if (customerIdentity == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setRegion(customerIdentity);

        entity = service.save(entity);

        CityResponse data = modelMapper.map(entity, CityResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<CityResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid CityRequest request
    ) {
        City entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Region customerIdentity = customerIdentityService.findById(request.getRegionId());
        entity.setRegion(customerIdentity);

        modelMapper.map(request, entity);
        entity = service.save(entity);

        CityResponse data = modelMapper.map(entity, CityResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<CityResponse> delete(
            @PathVariable Integer id
    ) {
        City entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        CityResponse data = modelMapper.map(entity, CityResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<CityResponse>> findAll() {
        List<City> entities = service.findAll();
        List<CityResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, CityResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<CityElement>> findAll(
            @Valid CitySearch model
            ) {
        City search = modelMapper.map(model, City.class);

        Page<City> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<City> entities = entityPage.toList();

        List<CityElement> models = entities.stream()
                .map(e -> modelMapper.map(e, CityElement.class))
                .collect(Collectors.toList());

        PagedList<CityElement> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }

    @PostMapping(value = "/upload/{id}", consumes = {"multipart/form-data"})
    public ResponseMessage<Object> upload(
            @PathVariable Integer id,
            ImageUploadRequest model
    ) throws IOException {
        City entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        fileService.upload(entity, model.getFile().getInputStream());

        return ResponseMessage.success(true);
    }

    @GetMapping("/download/{id}.png")
    public void download(
            @PathVariable Integer id,
            HttpServletResponse response
    ) throws IOException {
        City entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
