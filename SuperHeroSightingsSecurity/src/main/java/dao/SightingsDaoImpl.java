package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Location;
import model.Sightings;
import model.SuperHero;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class SightingsDaoImpl implements SightingsDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String SQL_INSERT_SIGHTING
            = "insert into sighting (sightDate,SightTime, superHeroId, locationId) values (?, ?, ?, ?) ";

    private static final String SQL_DELETE_SIGHTING
            = " delete from sighting where sightingId = ? ";

    private static final String SQL_UPDATE_SIGHTING
            = "update sighting set sightDate = ?, SightTime=?, superHero.superHeroId = ?, location.locationId  where sightingId =  ? ";

    private static final String SQL_SELECT_SIGHTING
            = "select * from sighting where sightingId = ? ";

    private static final String SQL_SELECT_ALL_SIGHTINGS = " select * from sighting ";

    private static final String SQL_SELECT_TEN_RECENT_SIGHTINGS = " select * from sighting order  by sighting.SightDate DESC , sighting.SightTime DESC limit 10";

    private static final String SQL_SELECT_HERO_BY_SIGHTINGID = "select superhero.SuperHeroId, HeroName, SuperPower, Description "
            + "from superHero inner join sighting on sighting.SuperHeroId = superhero.SuperHeroId where sightingId= ? ";

    private static final String SQL_SELECT_LOCATION_BY_SIGHTINGID = "select sighting.LocationId, LocationName, Description, Address, Latitude, Longitude from location "
            + "inner join sighting on sighting.LocationId = location.LocationId where sightingId= ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sightings sightings) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sightings.getSightingDate().toString(),
                sightings.getSightingTime().toString(),
                sightings.getSuperHero().getSuperHeroId(),
                sightings.getLocation().getLocationId());

        int sightingId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        sightings.setSightingsId(sightingId);
    }

    @Override
    public void deleteSighting(Sightings sightings) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightings.getSightingsId());
    }

    @Override
    public void updateSighting(Sightings sightings) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sightings.getSightingDate().toString(),
                sightings.getSightingTime().toString(),
                sightings.getSuperHero().getSuperHeroId(),
                sightings.getLocation().getLocationId(),
                sightings.getSightingsId());
    }

    @Override
    public Sightings getSightingById(int sightingsId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingsMapper(), sightingsId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sightings> getAllSightings() {
        List<Sightings> sightings = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingsMapper());
        return getHeroAndLocationForSightings(sightings);
    }

    @Override
    public List<Sightings> getTenRecentSightings() {
        List<Sightings> sightings = jdbcTemplate.query(SQL_SELECT_TEN_RECENT_SIGHTINGS, new SightingsMapper());
        return getHeroAndLocationForSightings(sightings);
    }

    private List<Sightings> getHeroAndLocationForSightings(List<Sightings> sightings) {
        for (Sightings currentSightings : sightings) {
            currentSightings.setSuperHero(superHeroBySightingId(currentSightings.getSightingsId()));
            currentSightings.setLocation(locationBySightingId(currentSightings.getSightingsId()));
        }
        return sightings;
    }

    //helper methods
    private SuperHero superHeroBySightingId(int sightingId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_HERO_BY_SIGHTINGID, new SuperHeroMapper(), sightingId);
    }

    private Location locationBySightingId(int sightingId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTINGID, new LocationMapper(), sightingId);
    }

    private static final class SuperHeroMapper implements RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int i) throws SQLException {
            SuperHero sh = new SuperHero();

            sh.setSuperHeroId(rs.getInt("superHeroId"));
            sh.setHeroName(rs.getString("heroName"));
            sh.setSuperPower(rs.getString("superPower"));
            sh.setDescription(rs.getString("description"));
            return sh;
        }
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

    private static final class SightingsMapper implements RowMapper<Sightings> {

        @Override
        public Sightings mapRow(ResultSet rs, int i) throws SQLException {
            Sightings s = new Sightings();
            s.setSightingsId(rs.getInt("SightingId"));
            s.setSightingDate(rs.getTimestamp("SightDate").toLocalDateTime().toLocalDate());
            s.setSightingTime(rs.getString("SightTime"));
            return s;
        }
    }

}
