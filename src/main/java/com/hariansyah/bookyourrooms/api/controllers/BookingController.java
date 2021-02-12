package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.BookingElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.BookingRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.BookingResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.BookingSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.BookingService;
import com.hariansyah.bookyourrooms.api.services.FileService;
import com.hariansyah.bookyourrooms.api.services.RoomService;
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

@RequestMapping("/booking")
@RestController
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomService roomService;

    @GetMapping("/{id}")
    public ResponseMessage<BookingResponse> findById(
            @PathVariable Integer id
    ) {
        Booking entity = service.findById(id);
        if(entity != null) {
            BookingResponse data = modelMapper.map(entity, BookingResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<BookingResponse> add(
            @RequestBody @Valid BookingRequest model
    ) {
        Booking entity = modelMapper.map(model, Booking.class);

        Room customerIdentity = roomService.findById(model.getRoomId());

        if (customerIdentity == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setRoom(customerIdentity);

        entity = service.save(entity);

        BookingResponse data = modelMapper.map(entity, BookingResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<BookingResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid BookingRequest request
    ) {
        Booking entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Room customerIdentity = roomService.findById(request.getRoomId());
        entity.setRoom(customerIdentity);

        modelMapper.map(request, entity);
        entity = service.save(entity);

        BookingResponse data = modelMapper.map(entity, BookingResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<BookingResponse>> findAll() {
        List<Booking> entities = service.findAll();
        List<BookingResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, BookingResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<BookingElement>> findAll(
            @Valid BookingSearch model
            ) {
        Booking search = modelMapper.map(model, Booking.class);

        Page<Booking> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Booking> entities = entityPage.toList();

        List<BookingElement> models = entities.stream()
                .map(e -> modelMapper.map(e, BookingElement.class))
                .collect(Collectors.toList());

        PagedList<BookingElement> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }
}
