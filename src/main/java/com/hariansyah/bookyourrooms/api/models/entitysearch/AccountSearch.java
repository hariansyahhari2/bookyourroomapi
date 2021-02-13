package com.hariansyah.bookyourrooms.api.models.entitysearch;

import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.enums.RoleEnum;
import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

public class AccountSearch extends PageSearch {

    private Integer id;

    private String username;

    private String email;

    private CustomerIdentity customerIdentity;

    private RoleEnum role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerIdentity getCustomerIdentity() {
        return customerIdentity;
    }

    public void setCustomerIdentity(CustomerIdentity customerIdentity) {
        this.customerIdentity = customerIdentity;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
