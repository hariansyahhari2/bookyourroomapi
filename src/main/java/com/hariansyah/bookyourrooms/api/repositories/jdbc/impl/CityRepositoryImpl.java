package com.hariansyah.bookyourrooms.api.repositories.jdbc.impl;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.repositories.jdbc.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<City> findAll() {
        List<City> cityList = jdbcTemplate.query("SELECT c.id, c.name, r.id as region_id, r.name FROM city c " +
                "LEFT JOIN region r " +
                "ON c.region_id = r.id " +
                "WHERE c.region_id IS NOT NULL", (rs, i) -> {
            City city = new City();
            Region region = new Region();
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));

            region.setId(rs.getInt("region_id"));
            region.setName(rs.getString("name"));
            city.setRegion(region);
            return city;
        });
        return cityList;
    }

    public City findById(Integer id) {
        List<City> cityList = jdbcTemplate.query("SELECT c.id, c.name, r.id as region_id, r.name FROM city c " +
                "LEFT JOIN region r " +
                "ON c.region_id = r.id " +
                "WHERE c.id=? AND c.region_id IS NOT NULL", (rs, i) -> {
            City city = new City();
            Region region = new Region();
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));

            region.setId(rs.getInt("region_id"));
            region.setName(rs.getString("name"));
            city.setRegion(region);
            return city;
        }, id);
        return cityList.get(0);
    }

    public Boolean save(City city) {
        String query = "INSERT INTO city values (?,?)";
        jdbcTemplate.update(query, city.getName(), city.getRegion().getId());
        return true;
    }

    public Boolean edit(City city) {
        String query = "INSERT INTO city values (?,?,?)";
        jdbcTemplate.update(query, city.getId(), city.getName(), city.getRegion().getId());
        return true;
    }

    public Boolean remove (Integer id) {
        String query = "DELETE FROM city WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }
}
