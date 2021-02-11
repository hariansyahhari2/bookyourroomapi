package com.hariansyah.bookyourrooms.api.entities;

import com.hariansyah.bookyourrooms.api.enums.IdentityCategoryEnum;

import javax.persistence.*;

@Table
@Entity(name = "account")
public class Account extends AbstractEntity<Integer> {

    public Account() {
    }

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToOne
    @JoinColumn(name = "customer_identity_id")
    private CustomerIdentity customerIdentity;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public CustomerIdentity getCustomerIdentity() {
        return customerIdentity;
    }

    public void setCustomerIdentity(CustomerIdentity customerIdentity) {
        this.customerIdentity = customerIdentity;
    }
}
