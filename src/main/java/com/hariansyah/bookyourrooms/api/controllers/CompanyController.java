package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.CompanyElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CompanyRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CompanyResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.CompanySearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import com.hariansyah.bookyourrooms.api.services.FileService;
import com.hariansyah.bookyourrooms.api.services.CityService;
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

@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<CompanyResponse> findById(
            @PathVariable Integer id
    ) {
        Company entity = service.findById(id);
        System.out.println("Found" + entity);
        if(entity != null) {
            CompanyResponse data = modelMapper.map(entity, CompanyResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<CompanyResponse> add(
            @RequestBody @Valid CompanyRequest model
    ) {
        Company entity = modelMapper.map(model, Company.class);

        City city = cityService.findById(model.getCityId());

        if (city == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCity(city);

        entity = service.save(entity);

        CompanyResponse data = modelMapper.map(entity, CompanyResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<CompanyResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid CompanyRequest request
    ) {
        Company entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        City city = cityService.findById(request.getCityId());
        entity.setCity(city);

        modelMapper.map(request, entity);
        entity = service.save(entity);

        CompanyResponse data = modelMapper.map(entity, CompanyResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<CompanyResponse> delete(
            @PathVariable Integer id
    ) {
        Company entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        CompanyResponse data = modelMapper.map(entity, CompanyResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<CompanyResponse>> findAll() {
        List<Company> entities = service.findAll();
        List<CompanyResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, CompanyResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<CompanyElement>> findAll(
            @Valid CompanySearch model
            ) {
        Company search = modelMapper.map(model, Company.class);

        Page<Company> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Company> entities = entityPage.toList();

        List<CompanyElement> models = entities.stream()
                .map(e -> modelMapper.map(e, CompanyElement.class))
                .collect(Collectors.toList());

        PagedList<CompanyElement> data = new PagedList<>(
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
        Company entity = service.findById(id);
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
        Company entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
