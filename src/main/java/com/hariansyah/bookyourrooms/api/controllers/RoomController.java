package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Hotel;
import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.RoomElement;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.RoomRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.RoomResponse;
import com.hariansyah.bookyourrooms.api.models.entitysearch.RoomSearch;
import com.hariansyah.bookyourrooms.api.models.fileupload.ImageUploadRequest;
import com.hariansyah.bookyourrooms.api.models.pagination.PagedList;
import com.hariansyah.bookyourrooms.api.services.HotelService;
import com.hariansyah.bookyourrooms.api.services.RoomService;
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

@RequestMapping("/room")
@RestController
public class RoomController {

    @Autowired
    private RoomService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseMessage<RoomResponse> findById(
            @PathVariable Integer id
    ) {
        Room entity = service.findById(id);
        if(entity != null) {
            RoomResponse data = modelMapper.map(entity, RoomResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<RoomResponse> add(
            @RequestBody @Valid RoomRequest model
    ) {
        Room entity = modelMapper.map(model, Room.class);

        Hotel hotel = hotelService.findById(model.getHotelId());

        if (hotel == null) {
            throw new ForeignKeyNotFoundException();
        }

        entity.setHotel(hotel);

        entity = service.save(entity);

        RoomResponse data = modelMapper.map(entity, RoomResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<RoomResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid RoomRequest request
    ) {
        Room entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        Hotel hotel = hotelService.findById(request.getHotelId());
        entity.setHotel(hotel);

        entity = service.save(entity);

        RoomResponse data = modelMapper.map(entity, RoomResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<RoomResponse> delete(
            @PathVariable Integer id
    ) {
        Room entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        RoomResponse data = modelMapper.map(entity, RoomResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<RoomResponse>> findAll() {
        List<Room> entities = service.findAll();
        List<RoomResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, RoomResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<RoomElement>> findAll(
            @Valid RoomSearch model
            ) {
        Room search = modelMapper.map(model, Room.class);

        Page<Room> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Room> entities = entityPage.toList();

        List<RoomElement> models = entities.stream()
                .map(e -> modelMapper.map(e, RoomElement.class))
                .collect(Collectors.toList());

        PagedList<RoomElement> data = new PagedList<>(
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
        Room entity = service.findById(id);
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
        Room entity = service.findById(id);
        if (entity == null) {
            throw new EntityExistsException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"" );
        fileService.download(entity, response.getOutputStream());
    }
}
