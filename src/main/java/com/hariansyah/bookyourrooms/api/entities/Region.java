package com.hariansyah.bookyourrooms.api.entities;

import com.hariansyah.bookyourrooms.api.enums.IdentityCategoryEnum;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "region")
public class Region extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "region")
    private List<City> cities;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
