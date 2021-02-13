package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.entities.Hotel;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.HotelElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.HotelRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.HotelResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.HotelSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;
import com.hariansyah.bookyourrooms.api.services.CityService;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import com.hariansyah.bookyourrooms.api.services.HotelService;
import com.hariansyah.bookyourrooms.api.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.hariansyah.bookyourrooms.api.models.validations.RoleValidation.validateRoleHotelManager;

@RequestMapping("/hotel")
@RestController
public class HotelController {

    @Autowired
    private HotelService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    private FileService fileService;

    private void validateManager(HttpServletRequest request) {
        validateRoleHotelManager(request, jwtTokenUtil, accountRepository);
    }

    @GetMapping("/{id}")
    public ResponseMessage<HotelResponse> findById(
            @PathVariable Integer id
    ) {
        Hotel entity = service.findById(id);
        if(entity != null) {
            HotelResponse data = modelMapper.map(entity, HotelResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<HotelResponse> add(
            @RequestBody @Valid HotelRequest model,
            HttpServletRequest request
    ) {
        validateManager(request);
        Hotel entity = modelMapper.map(model, Hotel.class);

        Company company = companyService.findById(model.getCompanyId());
        City city = cityService.findById(model.getCityId());

        if (company == null || city == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCompany(company);
        entity.setCity(city);

        entity = service.save(entity);

        HotelResponse data = modelMapper.map(entity, HotelResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<HotelResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid HotelRequest model,
            HttpServletRequest request
    ) {
        validateManager(request);
        Hotel entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Company company = companyService.findById(model.getCompanyId());
        City city = cityService.findById(model.getCityId());
        if (company == null || city == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setCompany(company);
        entity.setCity(city);

        entity = service.save(entity);

        HotelResponse data = modelMapper.map(entity, HotelResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<HotelResponse> delete(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        validateManager(request);
        Hotel entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        HotelResponse data = modelMapper.map(entity, HotelResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<HotelResponse>> findAll() {
        List<Hotel> entities = service.findAll();
        List<HotelResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, HotelResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<HotelElement>> findAll(
            @Valid HotelSearch model
            ) {
        Hotel search = modelMapper.map(model, Hotel.class);

        Page<Hotel> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Hotel> entities = entityPage.toList();

        List<HotelElement> models = entities.stream()
                .map(e -> modelMapper.map(e, HotelElement.class))
                .collect(Collectors.toList());

        PagedList<HotelElement> data = new PagedList<>(
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
            ImageUploadRequest model,
            HttpServletRequest request
    ) throws IOException {
        validateManager(request);
        Hotel entity = service.findById(id);
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
        Hotel entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
