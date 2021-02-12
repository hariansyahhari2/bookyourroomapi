package com.hariansyah.bookyourrooms.api.entities;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CONFIRMED;

@Table
@Entity(name = "booking")
public class Booking extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;

    @Column(name = "person_count")
    private Integer personCount;

    @ManyToOne
    @JoinColumn(name = "booked_by")
    private Account bookedBy;

    @ManyToOne
    @JoinColumn(name = "guest")
    private CustomerIdentity guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private Double subTotal;

    @Enumerated
    @Column
    private StatusEnum status = CONFIRMED;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Account getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(Account bookedBy) {
        this.bookedBy = bookedBy;
    }

    public CustomerIdentity getGuest() {
        return guest;
    }

    public void setGuest(CustomerIdentity guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
