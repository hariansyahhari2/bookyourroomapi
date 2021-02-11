package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import com.hariansyah.bookyourrooms.api.enums.IdentityCategoryEnum;

public class CustomerIdentityRequest {

    private Integer id;

    private IdentityCategoryEnum identity_category;

    private String identificationNumber;

    private String firstName;

    private String lastName;

    private String address;

    public Integer getId() {
        return id;
    }

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
