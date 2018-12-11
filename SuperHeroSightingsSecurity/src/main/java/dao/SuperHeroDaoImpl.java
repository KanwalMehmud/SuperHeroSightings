package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.SuperHero;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class SuperHeroDaoImpl implements SuperHeroDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String SQL_INSERT_SUPERHERO
            = "insert into superhero (heroName, superPower, description) values (?, ?, ? ) ";

    private static final String SQL_DELETE_SUPERHERO
            = " delete from superhero where superHeroId = ? ";

    private static final String SQL_UPDATE_SUPERHERO
            = "update superhero set heroName = ?, superPower = ?, description = ? where superHeroId =  ?";

    private static final String SQL_SELECT_SUPERHERO
            = "select * from superhero where superHeroId = ?";

    private static final String SQL_SELECT_SUPERHERO_BY_LOCATION_ID
            = " select superhero.superHeroId, superhero.heroName, superHero.superPower, superHero.description, location.locationName, location.address, location.latitude, location.longitude "
            + " from superhero join sighting on sighting.superHeroId = superhero.superHeroId "
            + " join location on sighting.locationId = location.locationId "
            + " where location.locationId = ? ";

    private static final String SQL_SELECT_ALL_SUPERHEROS
            = "select * from SuperHero ";

    private static final String SQL_SELECT_SUPERHERO_BY_ORGANIZATION_ID
            = " select superhero.superHeroId, superhero.HeroName, superhero.SuperPower, superhero.Description from superhero "
            + " inner join superhero_organization on superhero_organization.SuperHeroId = superhero.SuperHeroId "
            + " inner join organization on superhero_organization.OrganizationId= organization.OrganizationId "
            + " where superhero_organization.OrganizationId= ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperHero addHero(SuperHero hero) {
        jdbcTemplate.update(SQL_INSERT_SUPERHERO,
                hero.getHeroName(),
                hero.getSuperPower(),
                hero.getDescription());

        int heroId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        hero.setSuperHeroId(heroId);
        return hero;
    }

    @Override
    public void deleteSuperHero(SuperHero hero) {
        jdbcTemplate.update(SQL_DELETE_SUPERHERO, hero.getSuperHeroId());
    }

    @Override
    public void updateSuperHero(SuperHero hero) {
        jdbcTemplate.update(SQL_UPDATE_SUPERHERO,
                hero.getHeroName(),
                hero.getSuperPower(),
                hero.getDescription(),
                hero.getSuperHeroId());
    }

    @Override
    public SuperHero getSuperHeroById(int SuperHeroId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SUPERHERO, new SuperHeroMapper(), SuperHeroId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperHero> getAllSuperHeros() {

        return jdbcTemplate.query(SQL_SELECT_ALL_SUPERHEROS,
                new SuperHeroMapper());
    }

    @Override
    public List<SuperHero> getAllSuperHerosByLocation(int locationId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERHERO_BY_LOCATION_ID, new SuperHeroMapper(), locationId);
    }

    @Override
    public List<SuperHero> getAllSuperHerosByOrganization(int organizationId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERHERO_BY_ORGANIZATION_ID, new SuperHeroMapper(), organizationId);
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

}

