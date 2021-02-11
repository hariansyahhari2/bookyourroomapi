package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.AccountElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.AccountRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.AccountResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.AccountSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.AccountService;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
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

@RequestMapping("/accounts")
@RestController
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerIdentityService customerIdentityService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<AccountResponse> findById(
            @PathVariable Integer id
    ) {
        Account entity = service.findById(id);
        if(entity != null) {
            AccountResponse data = modelMapper.map(entity, AccountResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<AccountResponse> add(
            @RequestBody @Valid AccountRequest model
    ) {
        Account entity = modelMapper.map(model, Account.class);

        CustomerIdentity customerIdentity = customerIdentityService.findById(model.getCustomerIdentityId());

        if (customerIdentity == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCustomerIdentity(customerIdentity);

        entity = service.save(entity);

        AccountResponse data = modelMapper.map(entity, AccountResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<AccountResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid AccountRequest request
    ) {
        Account entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        CustomerIdentity customerIdentity = customerIdentityService.findById(request.getCustomerIdentityId());
        entity.setCustomerIdentity(customerIdentity);

        modelMapper.map(request, entity);
        entity = service.save(entity);

        AccountResponse data = modelMapper.map(entity, AccountResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<AccountResponse> delete(
            @PathVariable Integer id
    ) {
        Account entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        AccountResponse data = modelMapper.map(entity, AccountResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<AccountResponse>> findAll() {
        List<Account> entities = service.findAll();
        List<AccountResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, AccountResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<AccountElement>> findAll(
            @Valid AccountSearch model
            ) {
        Account search = modelMapper.map(model, Account.class);

        Page<Account> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Account> entities = entityPage.toList();

        List<AccountElement> models = entities.stream()
                .map(e -> modelMapper.map(e, AccountElement.class))
                .collect(Collectors.toList());

        PagedList<AccountElement> data = new PagedList<>(
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
        Account entity = service.findById(id);
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
        Account entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
