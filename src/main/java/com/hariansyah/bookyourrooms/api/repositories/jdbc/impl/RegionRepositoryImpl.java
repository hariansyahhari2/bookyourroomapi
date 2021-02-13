package com.hariansyah.bookyourrooms.api.repositories.jdbc.impl;

import com.hariansyah.bookyourrooms.api.entities.Region;
import com.hariansyah.bookyourrooms.api.repositories.jdbc.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionRepositoryImpl implements RegionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Region> findAll() {
        String query = "SELECT * FROM region";
        List<Region> regions = jdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(Region.class
        ));
        return regions;
    }

    @Override
    public Region findById(Integer id) {
        List<Region> regionList = jdbcTemplate.query("SELECT * FROM region where id=?", (rs, i) -> {
            Region region = new Region();
            region.setId(rs.getInt("id"));
            region.setName(rs.getString("name"));
            return region;
        }, id);
        return regionList.get(0);
    }

    @Override
    public Boolean save (Region region) {
        String query = "INSERT INTO region values (?)";
        jdbcTemplate.update(query, region.getName());
        return true;
    }

    @Override
    public Boolean edit(Region region) {
        String query = "INSERT INTO region values (?, ?)";
        jdbcTemplate.update(query, region.getId(), region.getName());
        return true;
    }

    @Override
    public Boolean remove (Integer id) {
        String query = "DELETE FROM region WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }
}