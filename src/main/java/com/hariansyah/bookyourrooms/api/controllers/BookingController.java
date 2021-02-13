package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.entities.Booking;
import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.exceptions.*;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.BookingRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.BookingResponse;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;
import com.hariansyah.bookyourrooms.api.repositories.BookingRepository;
import com.hariansyah.bookyourrooms.api.services.AccountService;
import com.hariansyah.bookyourrooms.api.services.BookingService;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import com.hariansyah.bookyourrooms.api.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.*;
import static java.time.temporal.ChronoUnit.DAYS;

@RequestMapping("/booking")
@RestController
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CustomerIdentityService customerIdentityService;

    @Autowired
    private BookingRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtToken jwtTokenUtil;

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
            @RequestBody @Valid BookingRequest model,
            HttpServletRequest request
    ) {
        Long numberOfNight = DAYS.between(model.getCheckInDate(), model.getCheckOutDate());
        if (numberOfNight <= 0) {
            throw new ForeignKeyNotFoundException();
        }
        Room room = roomService.findById(model.getRoomId());
        if (room == null) {
            throw new ForeignKeyNotFoundException();
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Account bookedBy = accountRepository.findByUsername(username);
            CustomerIdentity guest = customerIdentityService.findById(model.getGuestId());

            if (!guest.getAccount().getUsername().equals(username)) {
                throw new InvalidCredentialsException();
            }

            Booking entity = modelMapper.map(model, Booking.class);
            LocalDate checkIn = model.getCheckInDate();
            LocalDate checkOut = model.getCheckOutDate();
            entity.setCheckInDate(checkIn);
            entity.setCheckOutDate(checkOut);
            entity.setBookedBy(bookedBy);
            entity.setGuest(guest);
            entity.setNumberOfNight(numberOfNight);

            List<Booking> checkRoom = repository.findNumberOfBooked(checkIn, checkOut, room.getId());

            Long numberOfBookedRoom = 0L;

            for (Booking checkedRoom : checkRoom) {
                numberOfBookedRoom += checkedRoom.getRoomCount();
            }

            if ((numberOfBookedRoom + model.getRoomCount()) <= room.getNumberOfRoom()) {
                Double subTotal = entity.getRoomCount() * room.getPrice() * numberOfNight;

                entity.setRoom(room);
                entity.setSubTotal(subTotal);
                entity.setStatus(CONFIRMED);
                entity = service.save(entity);

                BookingResponse data = modelMapper.map(entity, BookingResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new RoomNotAvailableException();
    }

    @GetMapping("/{id}/cancel")
    public ResponseMessage<BookingResponse> cancel(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        Booking entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        if (entity.getStatus().equals(CANCELLED)) {
            throw new StatusCancelledException();
        }

        if (entity.getStatus().equals(CHECKED_IN)) {
            throw new StatusCheckedInException();
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (!entity.getBookedBy().getUsername().equals(username)) {
                throw new InvalidCredentialsException();
            }
            entity.setStatus(CANCELLED);
            entity = service.save(entity);
            BookingResponse data = modelMapper.map(entity, BookingResponse.class);
            return ResponseMessage.success(data);
        }
        throw new ForeignKeyNotFoundException();
    }


    @GetMapping("/{id}/check-in")
    public ResponseMessage<BookingResponse> checkIn(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        Booking entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        if (entity.getStatus().equals(CANCELLED)) {
            throw new StatusCancelledException();
        }
        if (entity.getStatus().equals(CHECKED_IN)) {
            throw new StatusCheckedInException();
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (!entity.getBookedBy().getUsername().equals(username)) {
                throw new InvalidCredentialsException();
            }
            entity.setStatus(CHECKED_IN);
            entity = service.save(entity);
            BookingResponse data = modelMapper.map(entity, BookingResponse.class);
            return ResponseMessage.success(data);
        }
        throw new ForeignKeyNotFoundException();
    }
}
