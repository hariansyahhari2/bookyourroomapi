package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.ContactPerson;
import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.InvalidCredentialsException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.ContactPersonRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.ContactPersonResponse;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;
import com.hariansyah.bookyourrooms.api.services.FileService;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import com.hariansyah.bookyourrooms.api.services.ContactPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.hariansyah.bookyourrooms.api.models.validations.RoleValidation.validateRoleManagerOREmployee;

@RequestMapping("/contact-person")
@RestController
public class ContactPersonController {

    @Autowired
    private ContactPersonService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    private FileService fileService;

    private void validateManagerOrEmployee(HttpServletRequest request) {
        validateRoleManagerOREmployee(request, jwtTokenUtil, accountRepository);
    }

    @GetMapping("/{id}")
    public ResponseMessage<ContactPersonResponse> findById(
            @PathVariable Integer id
    ) {
        ContactPerson entity = service.findById(id);

        ContactPersonResponse data = modelMapper.map(entity, ContactPersonResponse.class);
        return ResponseMessage.success(data);
    }

    @PostMapping
    public ResponseMessage<Boolean> add(
            @RequestBody @Valid ContactPersonRequest model,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) throw new InvalidCredentialsException();
        validateManagerOrEmployee(request);
        ContactPerson entity = modelMapper.map(model, ContactPerson.class);

        Company customerIdentity = companyService.findById(model.getCompanyId());

        if (customerIdentity == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCompany(customerIdentity);
        return ResponseMessage.success(service.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseMessage<Boolean> edit(
            @PathVariable Integer id,
            @RequestBody @Valid ContactPersonRequest model,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) throw new InvalidCredentialsException();
        validateManagerOrEmployee(request);
        ContactPerson entity = service.findById(id);

        Company customerIdentity = companyService.findById(model.getCompanyId());
        entity.setCompany(customerIdentity);

        modelMapper.map(model, entity);
        return ResponseMessage.success(service.edit(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> delete(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token == null) throw new InvalidCredentialsException();
        validateManagerOrEmployee(request);
        service.findById(id);

        return ResponseMessage.success(service.removeById(id));
    }

    @GetMapping
    public ResponseMessage<List<ContactPersonResponse>> findAll() {
        List<ContactPerson> entities = service.findAll();
        List<ContactPersonResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, ContactPersonResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @PostMapping(value = "/upload/{id}", consumes = {"multipart/form-data"})
    public ResponseMessage<Object> upload(
            @PathVariable Integer id,
            ImageUploadRequest model,
            HttpServletRequest request
    ) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null) throw new InvalidCredentialsException();

        ContactPerson entity = service.findById(id);

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
