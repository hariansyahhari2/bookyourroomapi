package com.hariansyah.bookyourrooms.api.entities;

import com.hariansyah.bookyourrooms.api.enums.IdentityCategoryEnum;

import javax.persistence.*;

@Table
@Entity(name = "customer_identity")
public class CustomerIdentity extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @Column(unique = true)
    private IdentityCategoryEnum identity_category;

    @Column(name = "identification_number")
    private String identificationNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public IdentityCategoryEnum getIdentity_category() {
        return identity_category;
    }

    public void setIdentity_category(IdentityCategoryEnum identity_category) {
        this.identity_category = identity_category;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
