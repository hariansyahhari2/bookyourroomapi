package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.RoomNotAvailableException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.BookingRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.BookingResponse;
import com.hariansyah.bookyourrooms.api.repositories.BookingRepository;
import com.hariansyah.bookyourrooms.api.services.AccountService;
import com.hariansyah.bookyourrooms.api.services.BookingService;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import com.hariansyah.bookyourrooms.api.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CANCELLED;
import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CHECKED_IN;

@RequestMapping("/booking")
@RestController
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerIdentityService customerIdentityService;

    @Autowired
    private BookingRepository repository;

    @Autowired
    private ModelMapper modelMapper;

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
        Room room = roomService.findById(model.getRoomId());
        if (room == null) {
            throw new ForeignKeyNotFoundException();
        }

        Booking entity = modelMapper.map(model, Booking.class);
        LocalDateTime checkIn = model.getCheckInDate().atTime(room.getHotel().getCheckInTime());
        LocalDateTime checkOut = model.getCheckOutDate().atTime(room.getHotel().getCheckOutTime());
        entity.setCheckInDate(checkIn);
        entity.setCheckOutDate(checkOut);

        List<Booking> checkRoom = repository.findNumberOfBooked(checkIn, checkOut, room.getId());

        checkRoom.stream().forEach(e -> System.out.println(e.toString()));

        if (checkRoom.size() < room.getNumberOfRoom()) {
            entity.setRoom(room);

            entity = service.save(entity);

            BookingResponse data = modelMapper.map(entity, BookingResponse.class);
            return ResponseMessage.success(data);
        }
        throw new RoomNotAvailableException();
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
