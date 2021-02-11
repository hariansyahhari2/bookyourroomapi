package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

public class AccountRequest {

    private Integer id;

    private String username;

    private String email;

    private String password;

    private Integer customerIdentityId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCustomerIdentityId() {
        return customerIdentityId;
    }

    public void setCustomerIdentityId(Integer customerIdentityId) {
        this.customerIdentityId = customerIdentityId;
    }
}
