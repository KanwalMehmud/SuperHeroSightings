package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Location;
import model.SuperHero;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class LocationDaoImpl implements LocationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String SQL_INSERT_LOCATION
            = "insert into location (locationName, description, address, latitude, longitude) values (?, ?, ?, ?, ?) ";

    private static final String SQL_DELETE_LOCATION
            = " delete from Location where locationId = ? ";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set locationName = ?, description = ?, address = ?, latitude = ?, longitude = ?  where locationId =  ? ";

    private static final String SQL_SELECT_LOCATION
            = "select * from location where locationId = ? ";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from location ";
    private static final String SQL_DELETE_HERO_FROM_ALL_SIGHTING
            = " delete from Sighting WHERE SuperHeroId  = ? AND LocationId = ?";

    private static final String SQL_DELETE_LOCATION_FROM_ALL_SIGHTINGS
            = " delete from Sighting WHERE LocationId  = ? ";

    private static final String SQL_DELETE_HERO_FROM_ALL_LOCATIONS
            = " delete from Sighting WHERE SuperHeroId  = ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        location.setLocationId(locationId);
        return location;
    }

    @Override
    public void deleteLocation(Location location) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, location.getLocationId());
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public void deleteHeroFromASighting(SuperHero hero, Location location) {
        jdbcTemplate.update(SQL_DELETE_HERO_FROM_ALL_SIGHTING, hero.getSuperHeroId(), location.getLocationId());
    }

    @Override
    public void deleteLocationFromAllSightings(Location location) {
        jdbcTemplate.update(SQL_DELETE_LOCATION_FROM_ALL_SIGHTINGS, location.getLocationId());
    }

    @Override
    public void deleteHeroFromAllLocations(SuperHero hero) {
        jdbcTemplate.update(SQL_DELETE_HERO_FROM_ALL_LOCATIONS, hero.getSuperHeroId());
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();

            l.setLocationId(rs.getInt("locationId"));
            l.setLocationName(rs.getString("locationName"));
            l.setDescription(rs.getString("description"));
            l.setAddress(rs.getString("address"));
            l.setLatitude((rs.getBigDecimal("Latitude")));
            l.setLongitude((rs.getBigDecimal("Longitude")));
            return l;

        }
    }

}
