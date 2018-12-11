package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Organization;
import model.SuperHero;
import model.SuperHeroOrganization;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OrganizationDaoImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into organization (orgName, description, address, phone) values (?, ?, ?, ?) ";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from organization where organizationId= ? ";

    private static final String SQL_UPDATE_ORGANIZATION
            = " update organization set orgName = ?, description = ?, address = ?, phone = ?  where organizationId =  ? ";

    private static final String SQL_SELECT_ORGANIZATION
            = "select * from organization where organizationId = ? ";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from organization ";

    private static final String SQL_INSERT_HERO_TO_ORGANIZATION
            = "insert into SuperHero_Organization ( SuperHeroId, OrganizationId ) values (?,?) ";

    private static final String SQL_SELECT_ALL_HEROS_ORGANIZATIONS
            = " select * from SuperHero_Organization ";

    private static final String SQL_DELETE_HERO_FROM_AN_ORGANIZATION
            = " delete from SuperHero_Organization WHERE SuperHeroId  = ? AND OrganizationId = ?";

    private static final String SQL_DELETE_HERO_FROM_ALL_ORGANIZATIONS
            = " delete from SuperHero_Organization WHERE SuperHeroId  = ? ";
    
    private static final String SQL_DELETE_ORGANIZATION_FROM_ALL_HEROS
            = " delete from SuperHero_Organization WHERE OrganizationId  = ? ";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone());

        int organizationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        organization.setOrganizationId(organizationId);
        return organization;
    }

    @Override
    public void deleteOrganization(Organization organization) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organization.getOrganizationId());
    }

    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getOrganizationId());

    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationMapper(),
                    organizationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Organization> getAllOrganization() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public void addHeroToOrganization(SuperHero hero, Organization organization) {
        jdbcTemplate.update(SQL_INSERT_HERO_TO_ORGANIZATION, hero.getSuperHeroId(), organization.getOrganizationId());
    }

    @Override
    public void deleteHeroFromOrganization(SuperHero hero, Organization organization) {
        jdbcTemplate.update(SQL_DELETE_HERO_FROM_AN_ORGANIZATION, hero.getSuperHeroId(), organization.getOrganizationId());
    }

    @Override
    public void deleteHeroFromAllOrganizations(SuperHero hero) {
        jdbcTemplate.update(SQL_DELETE_HERO_FROM_ALL_ORGANIZATIONS, hero.getSuperHeroId());
    }

    @Override
    public List<SuperHeroOrganization> getAllHerosAndOrganization() {
        return jdbcTemplate.query(SQL_SELECT_ALL_HEROS_ORGANIZATIONS, new HeroOrganizationMapper());
    }

    @Override
    public void deleteOrganizationFromAllHeros(Organization organization) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION_FROM_ALL_HEROS, organization.getOrganizationId());
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

       
        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization o = new Organization();

            o.setOrganizationId(rs.getInt("organizationId"));
            o.setOrganizationName(rs.getString("OrgName"));
            o.setDescription(rs.getString("Description"));
            o.setAddress(rs.getString("Address"));
            o.setPhone(rs.getString("Phone"));
            return o;
        }
    }

    private static final class HeroOrganizationMapper implements RowMapper<SuperHeroOrganization> {

        @Override
        public SuperHeroOrganization mapRow(ResultSet rs, int i) throws SQLException {
            SuperHeroOrganization shOrg = new SuperHeroOrganization();
            shOrg.setSuperHeroId(rs.getInt("SuperHeroId"));
            shOrg.setOrganizationId(rs.getInt("OrganizationId"));
            return shOrg;
        }
    }
}
