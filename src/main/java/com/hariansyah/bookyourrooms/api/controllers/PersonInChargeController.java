package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.entities.PersonInCharge;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.PersonInChargeElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.PersonInChargeRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.PersonInChargeResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.PersonInChargeSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import com.hariansyah.bookyourrooms.api.services.PersonInChargeService;
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

@RequestMapping("/person-in-charge")
@RestController
public class PersonInChargeController {

    @Autowired
    private PersonInChargeService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<PersonInChargeResponse> findById(
            @PathVariable Integer id
    ) {
        PersonInCharge entity = service.findById(id);
        System.out.println("Found" + entity);
        if(entity != null) {
            PersonInChargeResponse data = modelMapper.map(entity, PersonInChargeResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<PersonInChargeResponse> add(
            @RequestBody @Valid PersonInChargeRequest model
    ) {
        PersonInCharge entity = modelMapper.map(model, PersonInCharge.class);

        Company company = companyService.findById(model.getCompanyId());

        if (company == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCompany(company);

        entity = service.save(entity);

        PersonInChargeResponse data = modelMapper.map(entity, PersonInChargeResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<PersonInChargeResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid PersonInChargeRequest request
    ) {
        PersonInCharge entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Company company = companyService.findById(request.getCompanyId());
        entity.setCompany(company);

        modelMapper.map(request, entity);
        entity = service.save(entity);

        PersonInChargeResponse data = modelMapper.map(entity, PersonInChargeResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<PersonInChargeResponse> delete(
            @PathVariable Integer id
    ) {
        PersonInCharge entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        PersonInChargeResponse data = modelMapper.map(entity, PersonInChargeResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<PersonInChargeResponse>> findAll() {
        List<PersonInCharge> entities = service.findAll();
        List<PersonInChargeResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, PersonInChargeResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<PersonInChargeElement>> findAll(
            @Valid PersonInChargeSearch model
            ) {
        PersonInCharge search = modelMapper.map(model, PersonInCharge.class);

        Page<PersonInCharge> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<PersonInCharge> entities = entityPage.toList();

        List<PersonInChargeElement> models = entities.stream()
                .map(e -> modelMapper.map(e, PersonInChargeElement.class))
                .collect(Collectors.toList());

        PagedList<PersonInChargeElement> data = new PagedList<>(
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
        PersonInCharge entity = service.findById(id);
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
        PersonInCharge entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
