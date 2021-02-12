package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.enums.StatusEnum;
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

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CANCELLED;
import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CHECKED_IN;

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

    @PostMapping("book")
    public ResponseMessage<BookingResponse> book(
            @RequestBody @Valid BookingRequest model
    ) {
        Booking entity = modelMapper.map(model, Booking.class);

        Room room = roomService.findById(model.getRoomId());

        if (room == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setRoom(room);

        entity = service.save(entity);

        BookingResponse data = modelMapper.map(entity, BookingResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}/cancel")
    public ResponseMessage<BookingResponse> cancel(
            @PathVariable Integer id
    ) {
        Booking entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        entity.setStatus(CANCELLED);
        entity = service.save(entity);

        BookingResponse data = modelMapper.map(entity, BookingResponse.class);
        return ResponseMessage.success(data);
    }


    @PutMapping("/{id}/check-in")
    public ResponseMessage<BookingResponse> checkIn(
            @PathVariable Integer id
    ) {
        Booking entity = service.findById(id);
        if(entity == null || entity.getStatus().equals(CANCELLED)) {
            throw new EntityNotFoundException();
        }

        entity.setStatus(CHECKED_IN);
        entity = service.save(entity);

        BookingResponse data = modelMapper.map(entity, BookingResponse.class);
        return ResponseMessage.success(data);
    }
}
