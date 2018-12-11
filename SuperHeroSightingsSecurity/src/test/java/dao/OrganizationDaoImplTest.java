package dao;

import java.util.List;
import model.Location;
import model.Organization;
import model.Sightings;
import model.SuperHero;
import model.SuperHeroOrganization;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrganizationDaoImplTest {

    SuperHeroDao superHeroDao;
    LocationDao locationDao;
    SightingsDao sightingsDao;
    OrganizationDao organizationDao;

    public OrganizationDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        superHeroDao = ctx.getBean("SuperHeroDao", SuperHeroDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        sightingsDao = ctx.getBean("SightingsDao", SightingsDao.class);
        organizationDao = ctx.getBean("OrganizationDao", OrganizationDao.class);

        List<SuperHero> superHero = superHeroDao.getAllSuperHeros();
        List<Location> locationList = locationDao.getAllLocations();
        List<Sightings> sightings = sightingsDao.getAllSightings();
        List<SuperHeroOrganization> organization = organizationDao.getAllHerosAndOrganization();
// delete records from organization bridge table
        sightings.forEach((s) -> {
            sightingsDao.deleteSighting(s);
        });
        locationList.forEach((location) -> {
            locationDao.deleteLocation(location);
        });
// delete records from organization bridge table

        for (SuperHeroOrganization shOrg : organization) {

            SuperHero hero = superHeroDao.getSuperHeroById(shOrg.getSuperHeroId());
            Organization org = organizationDao.getOrganizationById(shOrg.getOrganizationId());

            organizationDao.deleteHeroFromOrganization(hero, org);
        }

        superHero.forEach((hero) -> {
            superHeroDao.deleteSuperHero(hero);
        });

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddOrganization() {
        Organization org = new Organization();
        org.setAddress("1 org address");
        org.setDescription("1 org description");
        org.setOrganizationName("Test Organization");
        org.setPhone("1234567890");
        Organization testOrg = organizationDao.addOrganization(org);
        assertEquals("1 org address", testOrg.getAddress());
        assertNotNull(testOrg.getOrganizationId());
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrganization() {
        Organization org = new Organization();
        org.setAddress("1 org address");
        org.setDescription("1 org description");
        org.setOrganizationName("Test Organization");
        org.setPhone("1234567890");
        Organization newOrganization = organizationDao.addOrganization(org);

        organizationDao.deleteOrganization(newOrganization);
        Organization testOrg2 = organizationDao.getOrganizationById(newOrganization.getOrganizationId());
        assertNull(testOrg2);

    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testUpdateOrganizationAndGetOrganizationById() {
        Organization org = new Organization();
        org.setAddress("1 org address");
        org.setDescription("1 org description");
        org.setOrganizationName("Test Organization");
        org.setPhone("1234567890");
        Organization testOrg = organizationDao.addOrganization(org);
        testOrg.setPhone("1212121212");
        organizationDao.updateOrganization(testOrg);
        Organization testOrg2 = organizationDao.getOrganizationById(org.getOrganizationId());
        assertNotEquals(testOrg2.getPhone(), "1234567890");
    }

}
