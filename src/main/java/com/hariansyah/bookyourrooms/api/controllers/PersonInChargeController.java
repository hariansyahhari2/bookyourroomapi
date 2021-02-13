package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.entities.ContactPerson;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.ContactPersonElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.ContactPersonRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.ContactPersonResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.ContactPersonSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import com.hariansyah.bookyourrooms.api.services.ContactPersonService;
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
    private ContactPersonService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<ContactPersonResponse> findById(
            @PathVariable Integer id
    ) {
        ContactPerson entity = service.findById(id);
        System.out.println("Found" + entity);
        if(entity != null) {
            ContactPersonResponse data = modelMapper.map(entity, ContactPersonResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<ContactPersonResponse> add(
            @RequestBody @Valid ContactPersonRequest model
    ) {
        ContactPerson entity = modelMapper.map(model, ContactPerson.class);

        Company company = companyService.findById(model.getCompanyId());

        if (company == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCompany(company);

        entity = service.save(entity);

        ContactPersonResponse data = modelMapper.map(entity, ContactPersonResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<ContactPersonResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid ContactPersonRequest request
    ) {
        ContactPerson entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Company company = companyService.findById(request.getCompanyId());
        entity.setCompany(company);

        entity = service.save(entity);

        ContactPersonResponse data = modelMapper.map(entity, ContactPersonResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<ContactPersonResponse> delete(
            @PathVariable Integer id
    ) {
        ContactPerson entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        ContactPersonResponse data = modelMapper.map(entity, ContactPersonResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<ContactPersonResponse>> findAll() {
        List<ContactPerson> entities = service.findAll();
        List<ContactPersonResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, ContactPersonResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<ContactPersonElement>> findAll(
            @Valid ContactPersonSearch model
            ) {
        ContactPerson search = modelMapper.map(model, ContactPerson.class);

        Page<ContactPerson> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<ContactPerson> entities = entityPage.toList();

        List<ContactPersonElement> models = entities.stream()
                .map(e -> modelMapper.map(e, ContactPersonElement.class))
                .collect(Collectors.toList());

        PagedList<ContactPersonElement> data = new PagedList<>(
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
        ContactPerson entity = service.findById(id);
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
        ContactPerson entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
